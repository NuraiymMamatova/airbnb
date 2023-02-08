package com.example.airbnbb7.db.service.serviceImpl;

import com.example.airbnbb7.config.jwt.JwtTokenUtil;
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
import com.example.airbnbb7.dto.response.*;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
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
            user.addRole(roleRepository.findByName("USER"));
            user.setPassword(passwordEncoder.encode(firebaseToken.getEmail()));
            user.setName(firebaseToken.getName());
            user.setEmail(firebaseToken.getEmail());
            setEmail(user.getEmail());
            setId(user.getId());
            userRepository.save(user);
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
    public ProfileResponse userProfile(String mainInUserProfile, String houseSorting, String sortingHousesByValue, String sortingHousesByRating) {
        ProfileResponse profileResponse = new ProfileResponse(UserRepository.getUserId(),userRepository.findById(UserRepository.getUserId()).get().getName(),userRepository.findById(UserRepository.getUserId()).get().getEmail());
        switch (mainInUserProfile) {
            case "Bookings" -> {
                for (Booking booking : userRepository.findById(UserRepository.getUserId()).get().getBookings()) {
                    ProfileBookingHouseResponse profileBookingHouseResponse = new ProfileBookingHouseResponse(booking.getHouse().getId(), booking.getHouse().getPrice(), booking.getHouse().getTitle(),
                            booking.getHouse().getDescriptionOfListing(), booking.getHouse().getMaxOfGuests(), booking.getHouse().getHouseType(),
                            rating.getRating(feedbackRepository.getAllFeedbackByHouseId(booking.getHouse().getId())),booking.getCheckIn(),booking.getCheckOut());
                    profileBookingHouseResponse.setImages(booking.getHouse().getImages());
                    profileBookingHouseResponse.setOwner(new UserResponse(booking.getHouse().getOwner().getId(),
                            booking.getHouse().getOwner().getName(), booking.getHouse().getOwner().getEmail(), booking.getHouse().getOwner().getImage()));
                    profileBookingHouseResponse.setLocation(new LocationResponse(booking.getHouse().getLocation().getId(),
                            booking.getHouse().getLocation().getTownOrProvince(), booking.getHouse().getLocation().getAddress(), booking.getHouse().getLocation().getRegion()));
                    profileResponse.addBookings(profileBookingHouseResponse);
                }
                profileResponse.setBookingsSize((long) profileResponse.getBookings().size());
                return profileResponse;
            }
            case "My announcement" -> {
                profileResponse = star(houseSorting, sortingHousesByValue, sortingHousesByRating);
                profileResponse.setMyAnnouncementSize((long) userRepository.findById(profileResponse.getId()).get().getAnnouncements().size());
                return profileResponse;
            }
            case "On moderation" -> {
                for (House house : userRepository.findById(UserRepository.getUserId()).get().getAnnouncements()) {
                    if (house.getHousesStatus().equals(HousesStatus.ON_MODERATION)) {
                        profileResponse.addOnModeration(houseResponseConverter.viewHouse(house));
                    }
                }
                profileResponse.setOnModerationSize((long) profileResponse.getOnModeration().size());
                return profileResponse;
            }
            default -> {
                return null;
            }
        }
    }

    private ProfileResponse houseType(String houseSorting) {
        ProfileResponse profileResponse = new ProfileResponse(UserRepository.getUserId(),userRepository.findById(UserRepository.getUserId()).get().getName(),userRepository.findById(UserRepository.getUserId()).get().getEmail());
        switch (houseSorting) {
            case "In wish list" -> {
                for (House house : userRepository.findById(UserRepository.getUserId()).get().getAnnouncements()) {
                    profileResponse.addMyAnnouncement(houseResponseConverter.viewHouse(house));
                }
                profileResponse.getMyAnnouncement().sort(Comparator.comparing(HouseResponse::getRating).reversed());
                return profileResponse;
            }
            case "Apartment" -> {
                for (House house : userRepository.findById(UserRepository.getUserId()).get().getAnnouncements()) {
                    if (house.getHouseType().toString().equals("APARTMENT")) {
                        profileResponse.addMyAnnouncement(houseResponseConverter.viewHouse(house));
                    }
                }
                return profileResponse;
            }
            case "House" -> {
                for (House house : userRepository.findById(UserRepository.getUserId()).get().getAnnouncements()) {
                    if (house.getHouseType().toString().equals("HOUSE")) {
                        profileResponse.addMyAnnouncement(houseResponseConverter.viewHouse(house));
                    }
                }
                return profileResponse;
            }
        }
        for (House house : userRepository.findById(UserRepository.getUserId()).get().getAnnouncements()) {
            profileResponse.addMyAnnouncement(houseResponseConverter.viewHouse(house));
        }
        return profileResponse;
    }

    private ProfileResponse price(String houseSorting, String sortingHousesByValue) {
        ProfileResponse profileResponse = houseType(houseSorting);
        switch (sortingHousesByValue) {
            case "Low to high" -> profileResponse.getMyAnnouncement().sort(Comparator.comparing(HouseResponse::getPrice));
            case "High to low" -> profileResponse.getMyAnnouncement().sort(Comparator.comparing(HouseResponse::getPrice).reversed());
            default -> {
                return profileResponse;
            }
        }
        return profileResponse;
    }

    private ProfileResponse star(String houseSorting, String sortingHousesByValue, String sortingHousesByRating) {
        ProfileResponse profileResponse1 = price(houseSorting, sortingHousesByValue);
        ProfileResponse profileResponse = new ProfileResponse(profileResponse1.getId(), profileResponse1.getProfileName(), profileResponse1.getProfileContact());
        switch (sortingHousesByRating) {
            case "One" -> {
                for (HouseResponse house : profileResponse1.getMyAnnouncement()) {
                    if (rating.getRating(feedbackRepository.getAllFeedbackByHouseId(house.getId())) > 0 &&
                            rating.getRating(feedbackRepository.getAllFeedbackByHouseId(house.getId())) <= 1) {
                        profileResponse.addMyAnnouncement(house);
                    }
                }
                return profileResponse;
            }
            case "Two" -> {
                for (HouseResponse house : profileResponse1.getMyAnnouncement()) {
                    if (rating.getRating(feedbackRepository.getAllFeedbackByHouseId(house.getId())) > 1 &&
                            rating.getRating(feedbackRepository.getAllFeedbackByHouseId(house.getId())) <= 2) {
                        profileResponse.addMyAnnouncement(house);
                    }
                }
                return profileResponse;
            }
            case "Three" -> {
                for (HouseResponse house : profileResponse1.getMyAnnouncement()) {
                    if (rating.getRating(feedbackRepository.getAllFeedbackByHouseId(house.getId())) > 2 &&
                            rating.getRating(feedbackRepository.getAllFeedbackByHouseId(house.getId())) <= 3) {
                        profileResponse.addMyAnnouncement(house);
                    }
                }
                return profileResponse;
            }
            case "Four" -> {
                for (HouseResponse house : profileResponse1.getMyAnnouncement()) {
                    if (rating.getRating(feedbackRepository.getAllFeedbackByHouseId(house.getId())) > 3 &&
                            rating.getRating(feedbackRepository.getAllFeedbackByHouseId(house.getId())) <= 4) {
                        profileResponse.addMyAnnouncement(house);
                    }
                }
                return profileResponse;
            }
            case "Five" -> {
                for (HouseResponse house : profileResponse1.getMyAnnouncement()) {
                    if (rating.getRating(feedbackRepository.getAllFeedbackByHouseId(house.getId())) > 4 &&
                            rating.getRating(feedbackRepository.getAllFeedbackByHouseId(house.getId())) <= 5) {
                        profileResponse.addMyAnnouncement(house);
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