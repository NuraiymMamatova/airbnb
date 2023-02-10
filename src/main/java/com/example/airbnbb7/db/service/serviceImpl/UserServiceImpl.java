package com.example.airbnbb7.db.service.serviceImpl;

import com.example.airbnbb7.config.jwt.JwtTokenUtil;
import com.example.airbnbb7.converter.response.HouseResponseConverter;
import com.example.airbnbb7.db.customClass.Rating;
import com.example.airbnbb7.db.entities.Booking;
import com.example.airbnbb7.db.entities.House;
import com.example.airbnbb7.db.entities.Role;
import com.example.airbnbb7.db.entities.User;
import com.example.airbnbb7.db.enums.HousesStatus;
import com.example.airbnbb7.db.repository.*;
import com.example.airbnbb7.db.service.UserService;
import com.example.airbnbb7.dto.request.UserRequest;
import com.example.airbnbb7.dto.response.LoginResponse;
import com.example.airbnbb7.dto.response.ProfileHouseResponse;
import com.example.airbnbb7.dto.response.ProfileResponse;
import com.example.airbnbb7.exceptions.BadCredentialsException;
import com.example.airbnbb7.exceptions.ExceptionResponse;
import com.example.airbnbb7.exceptions.NotFoundException;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

    public LoginResponse authWithGoogle(String tokenId) {
        FirebaseToken firebaseToken;
        try {
            firebaseToken = FirebaseAuth.getInstance().verifyIdToken(tokenId);
        } catch (FirebaseAuthException firebaseAuthException) {
            throw new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, firebaseAuthException.getClass().getSimpleName(), firebaseAuthException.getMessage());
        }

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
    public ProfileResponse userProfile(String mainInUserProfile, String houseSorting, String sortingHousesByValue, String sortingHousesByRating, Long userId, int page, int size) {
        houseSorting = (houseSorting != null) ? houseSorting : " ";
        sortingHousesByValue = (sortingHousesByValue != null) ? sortingHousesByValue : " ";
        sortingHousesByRating = (sortingHousesByRating != null) ? sortingHousesByRating : " ";
        ProfileResponse profileResponse = new ProfileResponse(userId, userRepository.findById(userId).get().getName(), userRepository.findById(userId).get().getEmail());
        profileResponse.setBookingsSize((long) userRepository.findById(userId).get().getBookings().size());
        profileResponse.setMyAnnouncementSize((long) userRepository.findById(userId).get().getAnnouncements().size());
        Long counter = 0L;
        for (House house : userRepository.findById(userId).get().getAnnouncements()) {
            if (house.getHousesStatus().equals(HousesStatus.ON_MODERATION)) {
                counter++;
            }
        }
        profileResponse.setOnModerationSize(counter);
        switch (mainInUserProfile) {
            case "Bookings" -> {
                for (Booking booking : userRepository.findById(userId).get().getBookings()) {
                    ProfileHouseResponse profileBookingHouseResponse = houseResponseConverter.view(booking.getHouse());
                    profileBookingHouseResponse.setCheckIn(booking.getCheckIn());
                    profileBookingHouseResponse.setCheckOut(booking.getCheckOut());
                    profileResponse.addProfileHouseResponse(profileBookingHouseResponse);
                }
                return profileResponse;
            }
            case "My announcement" -> {
                ProfileResponse profileBookingHouseResponse = star(houseSorting, sortingHousesByValue, sortingHousesByRating, userId);
                profileBookingHouseResponse.setBookingsSize(profileResponse.getBookingsSize());
                profileBookingHouseResponse.setOnModerationSize(profileResponse.getOnModerationSize());
                profileBookingHouseResponse.setMyAnnouncementSize(profileResponse.getMyAnnouncementSize());
                profileBookingHouseResponse.setProfileHouseResponses(getProfileHouseResponse(page, size, profileBookingHouseResponse.getProfileHouseResponses()));
                return profileBookingHouseResponse;
            }
            case "On moderation" -> {
                for (House house : userRepository.findById(userId).get().getAnnouncements()) {
                    if (house.getHousesStatus().equals(HousesStatus.ON_MODERATION)) {
                        profileResponse.addProfileHouseResponse(houseResponseConverter.view(house));
                    }
                }
                profileResponse.setProfileHouseResponses(getProfileHouseResponse(page, size, profileResponse.getProfileHouseResponses()));
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
                    profileResponse.addProfileHouseResponse(houseResponseConverter.view(house));
                }
                profileResponse.getProfileHouseResponses().sort(Comparator.comparing(ProfileHouseResponse::getRating).reversed());
                return profileResponse;
            }
            case "Apartment" -> {
                for (House house : userRepository.findById(userId).get().getAnnouncements()) {
                    if (house.getHouseType().toString().equals("APARTMENT")) {
                        profileResponse.addProfileHouseResponse(houseResponseConverter.view(house));
                    }
                }
                return profileResponse;
            }
            case "House" -> {
                for (House house : userRepository.findById(userId).get().getAnnouncements()) {
                    if (house.getHouseType().toString().equals("HOUSE")) {
                        profileResponse.addProfileHouseResponse(houseResponseConverter.view(house));
                    }
                }
                return profileResponse;
            }
        }
        for (House house : userRepository.findById(userId).get().getAnnouncements()) {
            profileResponse.addProfileHouseResponse(houseResponseConverter.view(house));
        }
        return profileResponse;
    }

    private ProfileResponse price(String houseSorting, String sortingHousesByValue, Long userId) {
        ProfileResponse profileResponse = houseType(houseSorting, userId);
        switch (sortingHousesByValue) {
            case "Low to high" -> {
                profileResponse.getProfileHouseResponses().sort(Comparator.comparing(ProfileHouseResponse::getPrice));
            }
            case "High to low" -> {
                profileResponse.getProfileHouseResponses().sort(Comparator.comparing(ProfileHouseResponse::getPrice).reversed());
            }
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
                for (ProfileHouseResponse house : profileResponse1.getProfileHouseResponses()) {
                    if (rating.getRating(feedbackRepository.getAllFeedbackByHouseId(house.getId())) > 0 &&
                            rating.getRating(feedbackRepository.getAllFeedbackByHouseId(house.getId())) <= 1) {
                        profileResponse.addProfileHouseResponse(house);
                    }
                }
                return profileResponse;
            }
            case "Two" -> {
                for (ProfileHouseResponse house : profileResponse1.getProfileHouseResponses()) {
                    if (rating.getRating(feedbackRepository.getAllFeedbackByHouseId(house.getId())) > 1 &&
                            rating.getRating(feedbackRepository.getAllFeedbackByHouseId(house.getId())) <= 2) {
                        profileResponse.addProfileHouseResponse(house);
                    }
                }
                return profileResponse;
            }
            case "Three" -> {
                for (ProfileHouseResponse house : profileResponse1.getProfileHouseResponses()) {
                    if (rating.getRating(feedbackRepository.getAllFeedbackByHouseId(house.getId())) > 2 &&
                            rating.getRating(feedbackRepository.getAllFeedbackByHouseId(house.getId())) <= 3) {
                        profileResponse.addProfileHouseResponse(house);
                    }
                }
                return profileResponse;
            }
            case "Four" -> {
                for (ProfileHouseResponse house : profileResponse1.getProfileHouseResponses()) {
                    if (rating.getRating(feedbackRepository.getAllFeedbackByHouseId(house.getId())) > 3 &&
                            rating.getRating(feedbackRepository.getAllFeedbackByHouseId(house.getId())) <= 4) {
                        profileResponse.addProfileHouseResponse(house);
                    }
                }
                return profileResponse;
            }
            case "Five" -> {
                for (ProfileHouseResponse house : profileResponse1.getProfileHouseResponses()) {
                    if (rating.getRating(feedbackRepository.getAllFeedbackByHouseId(house.getId())) > 4 &&
                            rating.getRating(feedbackRepository.getAllFeedbackByHouseId(house.getId())) <= 5) {
                        profileResponse.addProfileHouseResponse(house);
                    }
                }
                return profileResponse;
            }
            default -> {
                return profileResponse1;
            }
        }
    }

    public List<ProfileHouseResponse> getProfileHouseResponse(int page, int size, List<ProfileHouseResponse> profileHouseResponses) {
        int startItem = (page - 1) * size;
        List<ProfileHouseResponse> list;

        if (profileHouseResponses.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + size, profileHouseResponses.size());
            list = profileHouseResponses.subList(startItem, toIndex);
        }

        return list;
    }
}