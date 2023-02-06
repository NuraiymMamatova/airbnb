package com.example.airbnbb7.db.service.serviceImpl;

import com.example.airbnbb7.config.jwt.JwtTokenUtil;
import com.example.airbnbb7.converter.response.HouseResponseConverter;
import com.example.airbnbb7.db.customClass.Rating;
import com.example.airbnbb7.db.entities.Booking;
import com.example.airbnbb7.db.entities.House;
import com.example.airbnbb7.db.entities.User;
import com.example.airbnbb7.db.enums.HousesStatus;
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

    private HouseResponseConverter houseResponseConverter;

    private Rating rating;

    private static Long userId;


    private String email;
    private final HouseRepository houseRepository;

    public String getEmail() {
        return email;
    }

    @Override
    public List<HouseResponse> userProfile(String mainInUserProfile, String houseSorting, String sortingHousesByValue, String sortingHousesByRating) {
        List<HouseResponse> houseResponses = new ArrayList<>();
        List<Booking> bookings = new ArrayList<>(userRepository.findById(UserRepository.getUserId()).get().getBookings());
        switch (mainInUserProfile) {
            case "Bookings" -> {
                for (Booking booking:bookings) {
                    HouseResponse houseResponse = new HouseResponse(booking.getHouse().getId(), booking.getHouse().getPrice(),
                            booking.getHouse().getTitle(), booking.getHouse().getDescriptionOfListing(), booking.getHouse().getMaxOfGuests(), booking.getHouse().getHouseType()
                    ,rating.getRating(booking.getHouse().getId()));
                    houseResponse.setOwner(new UserResponse(booking.getHouse().getOwner().getId(),
                            booking.getHouse().getOwner().getName(), booking.getHouse().getOwner().getEmail(), booking.getHouse().getOwner().getImage()));
                    houseResponse.setLocation(new LocationResponse(booking.getHouse().getLocation().getId(),
                            booking.getHouse().getLocation().getTownOrProvince(), booking.getHouse().getLocation().getAddress(), booking.getHouse().getLocation().getRegion()));
                    houseResponse.setImages(booking.getHouse().getImages());
                    houseResponses.add(houseResponse);
                }
                return houseResponses;
            }
            case "My announcement" -> {
                return sortingHousesByRating(houseSorting,sortingHousesByValue,sortingHousesByRating);
            }
            case "On moderation" -> {
                for (House house: userRepository.findById(UserRepository.getUserId()).get().getAnnouncements()) {
                    if (house.getHousesStatus().equals(HousesStatus.ON_MODERATION)){
                        houseResponses.add(houseResponseConverter.viewHouse(house));
                    }
                }
                return houseResponses;
            }
        }
        return null;
    }

    private List<HouseResponse> houseSorting(String houseSorting) {
        List<HouseResponse> houseResponses = new ArrayList<>();
        List<HouseResponse> houseResponseList = new ArrayList<>();
        for (House house : userRepository.findById(UserRepository.getUserId()).get().getAnnouncements()) {
            houseResponses.add(houseResponseConverter.viewHouse(house));
        }
        switch (houseSorting) {
            case "In wish list" -> {
                return houseResponses;
            }
            case "Apartment" -> {
                for (House house : userRepository.findById(UserRepository.getUserId()).get().getAnnouncements()) {
                    if (house.getHouseType().toString().equals("APARTMENT")) {
                        houseResponseList.add(houseResponseConverter.viewHouse(house));
                    }
                }
                return houseResponseList;
            }
            case "House" -> {
                for (House house : userRepository.findById(UserRepository.getUserId()).get().getAnnouncements()) {
                    if (house.getHouseType().toString().equals("HOUSE")) {
                        houseResponseList.add(houseResponseConverter.viewHouse(house));
                    }
                    return houseResponseList;
                }
            }
        }
        return houseResponses;
    }

    private List<HouseResponse> sortingHousesByValue(String houseSorting, String sortingHousesByValue) {
        List<HouseResponse> houseResponses = new ArrayList<>(houseSorting(houseSorting));
        switch (sortingHousesByValue) {
            case "Low to high" -> {
                houseResponses.sort(Comparator.comparing(HouseResponse::getPrice));
                return houseResponses;
            }
            case "High to low" -> {
                houseResponses.sort(Comparator.comparing(HouseResponse::getPrice).reversed());
                return houseResponses;
            }
        }
        return houseResponses;
    }

    private List<HouseResponse> sortingHousesByRating(String houseSorting, String sortingHousesByValue, String sortingHousesByRating) {
        List<HouseResponse> houseResponses = new ArrayList<>(sortingHousesByValue(houseSorting, sortingHousesByValue));
        List<HouseResponse> houseResponseList = new ArrayList<>();
        switch (sortingHousesByRating) {
            case "One" -> {
                for (HouseResponse house : houseResponses) {
                    if (house.getRating() > 0 && house.getRating() <= 1) {
                        houseResponseList.add(house);
                    }
                }
                return houseResponseList;
            }
            case "Two" -> {
                for (HouseResponse house : houseResponses) {
                    if (house.getRating() > 1 && house.getRating() <= 2) {
                        houseResponseList.add(house);
                    }
                }
                return houseResponseList;
            }
            case "Three" -> {
                for (HouseResponse house : houseResponses) {
                    if (house.getRating() > 2 && house.getRating() <= 3) {
                        houseResponseList.add(house);
                    }
                }
                return houseResponseList;
            }
            case "Four" -> {
                for (HouseResponse house : houseResponses) {
                    if (house.getRating() > 3 && house.getRating() <= 4) {
                        houseResponseList.add(house);
                    }
                }
                return houseResponseList;
            }
            case "Five" -> {
                for (HouseResponse house : houseResponses) {
                    if (house.getRating() > 4 && house.getRating() <= 5) {
                        houseResponseList.add(house);
                    }
                }
                return houseResponseList;
            }
        }
        return houseResponses;
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
            setUserId(user.getId());
            userRepository.save(user);
        }

        user = userRepository.findByEmail(firebaseToken.getEmail()).orElseThrow(() -> new NotFoundException(String.format("User %s not found!", firebaseToken.getEmail())));
        setUserId(user.getId());
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
        setUserId(user.getId());
        return new LoginResponse(jwtTokenUtil.generateToken(user), user.getEmail(), roleRepository.findRoleByUserId(user.getId()).getNameOfRole());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("not found email"));
    }

    public static Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}