package com.example.airbnbb7.db.service.serviceImpl;

import com.example.airbnbb7.config.jwt.JwtTokenUtil;
import com.example.airbnbb7.db.entities.Role;
import com.example.airbnbb7.converter.response.HouseResponseConverter;
import com.example.airbnbb7.db.customClass.Rating;
import com.example.airbnbb7.db.entities.Booking;
import com.example.airbnbb7.db.entities.House;
import com.example.airbnbb7.db.entities.User;
import com.example.airbnbb7.db.enums.HousesStatus;
import com.example.airbnbb7.db.repository.FeedbackRepository;
import com.example.airbnbb7.db.repository.HouseRepository;
import com.example.airbnbb7.db.repository.RoleRepository;
import com.example.airbnbb7.db.repository.UserRepository;
import com.example.airbnbb7.db.service.UserService;
import com.example.airbnbb7.dto.request.UserRequest;
import com.example.airbnbb7.dto.response.LoginResponse;
import com.example.airbnbb7.exceptions.BadCredentialsException;
import com.example.airbnbb7.exceptions.NotFoundException;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenUtil jwtTokenUtil;

    private final RoleRepository roleRepository;

    private static Long id;

    private final HouseResponseConverter houseResponseConverter;

    private final Rating rating;

    private String email;
    private final FeedbackRepository feedbackRepository;
    private final HouseRepository houseRepository;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @PostConstruct
    void init() throws IOException {
        GoogleCredentials googleCredentials =
                GoogleCredentials.fromStream(new ClassPathResource("airbnb-b7.json").getInputStream());
        FirebaseOptions firebaseOptions = FirebaseOptions.builder()
                .setCredentials(googleCredentials)
                .build();
        FirebaseApp firebaseApp = FirebaseApp.initializeApp(firebaseOptions);
    }

    public LoginResponse authWithGoogle(String tokenId) throws FirebaseAuthException {
        FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(tokenId);

        User user;
        if (userRepository.findByEmail(firebaseToken.getEmail()).isEmpty()) {
            user = new User();
            Role role = roleRepository.findByName("USER");
            role.addUser(user);
            user.addRole(role);
            user.setPassword(passwordEncoder.encode(firebaseToken.getEmail()));
            user.setName(firebaseToken.getName());
            user.setEmail(firebaseToken.getEmail());
            roleRepository.save(role);

        }

        user = userRepository.findByEmail(firebaseToken.getEmail()).orElseThrow(() -> new NotFoundException(String.format("User %s not found!", firebaseToken.getEmail())));
        setId(user.getId());
        setEmail(user.getEmail());
        String token = jwtTokenUtil.generateToken(user);
        return new LoginResponse(user.getEmail(), token, userRepository.findRoleByUserEmail(user.getEmail()).getNameOfRole());
    }

    public LoginResponse login(@RequestBody UserRequest request) {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(request.getEmail(),
                        request.getPassword());
        User user = userRepository.findByEmail(token.getName()).orElseThrow(
                () -> {
                    throw new NotFoundException("the user with this email was not found");
                });
        if (request.getPassword() == null) {
            throw new NotFoundException("Password must not be empty");
        }
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("invalid password");
        }
        setEmail(user.getEmail());
        setId(user.getId());
        return new LoginResponse(jwtTokenUtil.generateToken(user), user.getEmail(), roleRepository.findRoleByUserId(user.getId()).getNameOfRole());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("not found email"));
    }

    public static Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public ProfileResponse userProfile(String mainInUserProfile, String houseSorting, String sortingHousesByValue, String sortingHousesByRating, Long userId) {
        houseSorting = (houseSorting != null) ? houseSorting : " ";
        sortingHousesByValue = (sortingHousesByValue != null) ? sortingHousesByValue : " ";
        sortingHousesByRating = (sortingHousesByRating != null) ? sortingHousesByRating : " ";
        ProfileResponse profileResponse = new ProfileResponse(UserRepository.getUserId(), userRepository.findById(UserRepository.getUserId()).get().getName(), userRepository.findById(UserRepository.getUserId()).get().getEmail());
        switch (mainInUserProfile) {
            case "Bookings" -> {
                for (Booking booking : userRepository.findById(userId).get().getBookings()) {
                    ProfileBookingHouseResponse profileBookingHouseResponse = houseResponseConverter.view(booking.getHouse());
                    profileBookingHouseResponse.setCheckIn(booking.getCheckIn());
                    profileBookingHouseResponse.setCheckOut(booking.getCheckOut());
                    profileResponse.addBookings(profileBookingHouseResponse);
                }
                profileResponse.setBookingsSize((long) profileResponse.getBookings().size());
                return profileResponse;
            }
            case "My announcement" -> {
                profileResponse = star(houseSorting, sortingHousesByValue, sortingHousesByRating, userId);
                profileResponse.setMyAnnouncementSize((long) userRepository.findById(userId).get().getAnnouncements().size());
                return profileResponse;
            }
            case "On moderation" -> {
                for (House house : userRepository.findById(userId).get().getAnnouncements()) {
                    if (house.getHousesStatus().equals(HousesStatus.ON_MODERATION)) {
                        profileResponse.addBookings(houseResponseConverter.view(house));
                    }
                }
                return profileResponse;
            }
            default -> {
                return profileResponse;
            }
        }
    }

    private ProfileResponse houseType(String houseSorting, Long userId) {
        ProfileResponse profileResponse = new ProfileResponse(userId, userRepository.findById(userId).get().getName(), userRepository.findById(userId).get().getEmail());
        switch (houseSorting) {
            case "In wish list" -> {
                for (House house : userRepository.findById(userId).get().getAnnouncements()) {
                    profileResponse.addBookings(houseResponseConverter.view(house));
                }
                profileResponse.getBookings().sort(Comparator.comparing(ProfileBookingHouseResponse::getRating).reversed());
                return profileResponse;
            }
            case "Apartment" -> {
                for (House house : userRepository.findById(userId).get().getAnnouncements()) {
                    if (house.getHouseType().toString().equals("APARTMENT")) {
                        profileResponse.addBookings(houseResponseConverter.view(house));
                    }
                }
                return profileResponse;
            }
            case "House" -> {
                for (House house : userRepository.findById(userId).get().getAnnouncements()) {
                    if (house.getHouseType().toString().equals("HOUSE")) {
                        profileResponse.addBookings(houseResponseConverter.view(house));
                    }
                }
                return profileResponse;
            }
        }
        for (House house : userRepository.findById(userId).get().getAnnouncements()) {
            profileResponse.addBookings(houseResponseConverter.view(house));
        }
        return profileResponse;
    }

    private ProfileResponse price(String houseSorting, String sortingHousesByValue, Long userId) {
        ProfileResponse profileResponse = houseType(houseSorting, userId);
        switch (sortingHousesByValue) {
            case "Low to high" ->
                    profileResponse.getBookings().sort(Comparator.comparing(ProfileBookingHouseResponse::getPrice));
            case "High to low" ->
                    profileResponse.getBookings().sort(Comparator.comparing(ProfileBookingHouseResponse::getPrice).reversed());
            default -> {
                return profileResponse;
            }
        }
        return profileResponse;
    }

    private ProfileResponse star(String houseSorting, String sortingHousesByValue, String sortingHousesByRating, Long userId) {
        ProfileResponse profileResponse1 = price(houseSorting, sortingHousesByValue, userId);
        ProfileResponse profileResponse = new ProfileResponse(profileResponse1.getId(), profileResponse1.getProfileName(), profileResponse1.getProfileContact());
        switch (sortingHousesByRating) {
            case "One" -> {
                for (ProfileBookingHouseResponse house : profileResponse1.getBookings()) {
                    if (rating.getRating(feedbackRepository.getAllFeedbackByHouseId(house.getId())) > 0 &&
                            rating.getRating(feedbackRepository.getAllFeedbackByHouseId(house.getId())) <= 1) {
                        profileResponse.addBookings(house);
                    }
                }
                return profileResponse;
            }
            case "Two" -> {
                for (ProfileBookingHouseResponse house : profileResponse1.getBookings()) {
                    if (rating.getRating(feedbackRepository.getAllFeedbackByHouseId(house.getId())) > 1 &&
                            rating.getRating(feedbackRepository.getAllFeedbackByHouseId(house.getId())) <= 2) {
                        profileResponse.addBookings(house);
                    }
                }
                return profileResponse;
            }
            case "Three" -> {
                for (ProfileBookingHouseResponse house : profileResponse1.getBookings()) {
                    if (rating.getRating(feedbackRepository.getAllFeedbackByHouseId(house.getId())) > 2 &&
                            rating.getRating(feedbackRepository.getAllFeedbackByHouseId(house.getId())) <= 3) {
                        profileResponse.addBookings(house);
                    }
                }
                return profileResponse;
            }
            case "Four" -> {
                for (ProfileBookingHouseResponse house : profileResponse1.getBookings()) {
                    if (rating.getRating(feedbackRepository.getAllFeedbackByHouseId(house.getId())) > 3 &&
                            rating.getRating(feedbackRepository.getAllFeedbackByHouseId(house.getId())) <= 4) {
                        profileResponse.addBookings(house);
                    }
                }
                return profileResponse;
            }
            case "Five" -> {
                for (ProfileBookingHouseResponse house : profileResponse1.getBookings()) {
                    if (rating.getRating(feedbackRepository.getAllFeedbackByHouseId(house.getId())) > 4 &&
                            rating.getRating(feedbackRepository.getAllFeedbackByHouseId(house.getId())) <= 5) {
                        profileResponse.addBookings(house);
                    }
                }
                return profileResponse;
            }
            default -> {
                return profileResponse1;
            }
        }
    }
}