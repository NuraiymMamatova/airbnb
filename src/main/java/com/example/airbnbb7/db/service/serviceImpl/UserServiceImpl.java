package com.example.airbnbb7.db.service.serviceImpl;

import com.example.airbnbb7.config.jwt.JwtTokenUtil;
import com.example.airbnbb7.converter.response.HouseResponseConverter;
import com.example.airbnbb7.db.customClass.Rating;
import com.example.airbnbb7.db.customClass.SimpleResponse;
import com.example.airbnbb7.db.entities.Booking;
import com.example.airbnbb7.db.entities.House;
import com.example.airbnbb7.db.entities.Role;
import com.example.airbnbb7.db.entities.User;
import com.example.airbnbb7.db.enums.HousesStatus;
import com.example.airbnbb7.db.repository.*;
import com.example.airbnbb7.db.service.EmailService;
import com.example.airbnbb7.db.service.MasterInterface;
import com.example.airbnbb7.db.service.UserService;
import com.example.airbnbb7.dto.request.UserRequest;
import com.example.airbnbb7.dto.response.*;
import com.example.airbnbb7.exceptions.BadCredentialsException;
import com.example.airbnbb7.exceptions.BadRequestException;
import com.example.airbnbb7.exceptions.ExceptionResponse;
import com.example.airbnbb7.exceptions.NotFoundException;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenUtil jwtTokenUtil;

    private final RoleRepository roleRepository;

    private final HouseResponseConverter houseResponseConverter;

    private final Rating rating;

    private final FeedbackRepository feedbackRepository;

    private final FavoriteHouseRepository favoriteHouseRepository;

    private final HouseRepository houseRepository;

    private final EmailService emailService;

    @PostConstruct
    void init() throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(new ClassPathResource("airbnb-b7.json").getInputStream());
        FirebaseOptions firebaseOptions = FirebaseOptions.builder().setCredentials(googleCredentials).build();
        FirebaseApp firebaseApp = FirebaseApp.initializeApp(firebaseOptions);
    }

    @Override
    public LoginResponse authWithGoogle(String tokenId) {
        FirebaseToken firebaseToken;
        try {
            firebaseToken = FirebaseAuth.getInstance().verifyIdToken(tokenId);
        } catch (FirebaseAuthException firebaseAuthException) {
            log.error("");
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
            user.setImage(firebaseToken.getPicture());
            roleRepository.save(role);
        }

        user = userRepository.findByEmail(firebaseToken.getEmail()).orElseThrow(() -> new NotFoundException(String.format("User %s not found!", firebaseToken.getEmail())));
        log.error("User %s not found!", firebaseToken.getEmail());
        String token = jwtTokenUtil.generateToken(user);
        return new LoginResponse(token, user.getEmail(), userRepository.findRoleByUserEmail(user.getEmail()).getNameOfRole());
    }

    @Override
    public LoginResponse login(UserRequest request) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        User user = userRepository.findByEmail(token.getName()).orElseThrow(() -> {
            throw new NotFoundException("the user with this email was not found");
        });
        if (request.getPassword() == null) {
            log.error("The email {} password not found", user.getEmail());
            throw new NotFoundException("Password must not be empty");
        }
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            log.error("The user {} invalid password", user.getEmail());
            throw new BadCredentialsException("invalid password");
        }
        return new LoginResponse(jwtTokenUtil.generateToken(user), user.getEmail(), roleRepository.findRoleByUserId(user.getId()).getNameOfRole());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("not found email"));
    }

    @Override
    public ProfileResponse userProfile(String mainInUserProfile, String sortHousesAsDesired, String sortHousesByApartments, String sortHousesByHouses, String sortingHousesByValue, String sortingHousesByRating, Authentication authentication, int page, int size) {
        if (authentication != null) {
            User user = (User) authentication.getPrincipal();
            Long userId = user.getId();
            sortHousesAsDesired = (sortHousesAsDesired != null) ? sortHousesAsDesired : " ";
            sortHousesByApartments = (sortHousesByApartments != null) ? sortHousesByApartments : " ";
            sortHousesByHouses = (sortHousesByHouses != null) ? sortHousesByHouses : " ";
            sortingHousesByValue = (sortingHousesByValue != null) ? sortingHousesByValue : " ";
            sortingHousesByRating = (sortingHousesByRating != null) ? sortingHousesByRating : " ";
            ProfileResponse profileResponse = new ProfileResponse(userId, userRepository.findById(userId).get().getName(), userRepository.findById(userId).get().getEmail());
            profileResponse.setPage((long) page);
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
                    int sizePage = (int) Math.ceil((double) profileResponse.getBookingsSize() / size);
                    profileResponse.setPageSize((long) sizePage);
                    return profileResponse;
                }
                case "My announcement" -> {
                    ProfileResponse profileBookingHouseResponse = star(sortHousesByApartments, sortHousesByHouses, sortHousesAsDesired, sortingHousesByValue, sortingHousesByRating, userId);
                    profileBookingHouseResponse.setBookingsSize(profileResponse.getBookingsSize());
                    profileBookingHouseResponse.setOnModerationSize(profileResponse.getOnModerationSize());
                    profileBookingHouseResponse.setMyAnnouncementSize(profileResponse.getMyAnnouncementSize());
                    if (profileBookingHouseResponse.getProfileHouseResponses() != null) {
                        profileBookingHouseResponse.setProfileHouseResponses(getProfileHouseResponse(page, size, profileBookingHouseResponse.getProfileHouseResponses()));
                        int sizePage = (int) Math.ceil((double) profileBookingHouseResponse.getProfileHouseResponses().size() / size);
                        profileBookingHouseResponse.setPageSize((long) sizePage);
                    } else profileBookingHouseResponse.setPageSize(0L);
                    profileBookingHouseResponse.setPage((long) page);
                    return profileBookingHouseResponse;
                }
                case "On moderation" -> {
                    for (House house : userRepository.findById(userId).get().getAnnouncements()) {
                        if (house.getHousesStatus().equals(HousesStatus.ON_MODERATION)) {
                            profileResponse.addProfileHouseResponse(houseResponseConverter.view(house));
                        }
                    }
                    if (profileResponse.getProfileHouseResponses() == null)
                        throw new NotFoundException("On moderation is null");
                    profileResponse.setProfileHouseResponses(getProfileHouseResponse(page, size, profileResponse.getProfileHouseResponses()));
                    int sizePage = (int) Math.ceil((double) profileResponse.getOnModerationSize() / size);
                    profileResponse.setPageSize((long) sizePage);
                    return profileResponse;
                }
                default -> {
                    return profileResponse;
                }
            }
        }
        log.error("The Authentication {} null", authentication.getPrincipal());
        throw new BadRequestException("Authentication cannot be null!");
    }

    @Override
    public List<UserAdminResponse> getAllUsers() {
        List<UserAdminResponse> users = userRepository.getAllUsers();
        List<UserAdminResponse> userAdminResponses = new ArrayList<>();
        for (UserAdminResponse user : users) {

            if (roleRepository.findRoleByUserId(user.getId()).getNameOfRole().equals("USER")) {
                userAdminResponses.add(user);
            }
        }
        log.info("users successfully show");
        return userAdminResponses;

    }

    @Override
    public SimpleResponse deleteUser(Long userId) {
        Role role = roleRepository.findRoleByUserId(userId);
        if (role.getNameOfRole().equals("USER")) {
            List<House> houseList = new ArrayList<>();
            for (Long id : houseRepository.getAllHouseIdByUserId(userId)) {
                houseList.add(houseRepository.findById(id).orElseThrow(() -> new NotFoundException("House id not found")));
            }
            houseRepository.deleteAll(houseList);
            roleRepository.deleteRoleByUserId(userId);
            userRepository.delete(userRepository.findById(userId).get());
        }
        log.info("user {} successfully deleted", userId);
        return new SimpleResponse("The user successfully deleted :)");
    }

    @Override
    public MasterInterface getUserById(Long userId, String bookingOrAnnouncement) {
        if (userId != null) {
            ProfileAdminResponse profileAdminResponse = userRepository.getUserByIdForAdmin(userId);
            if (bookingOrAnnouncement != null && bookingOrAnnouncement.equals("Bookings")) {
                List<HouseResponseForAdminUsers> houseResponseForAdminUsers = userRepository.getBooking(userId);
                houseResponseForAdminUsers.forEach(h -> {
                    House house = houseRepository.findById(h.getId()).orElseThrow(() -> new NotFoundException("House not found!"));
                    h.setImages(houseRepository.findImagesByHouseId(house.getId()));
                    h.setLocationResponse(new LocationResponse(house.getLocation().getId(), house.getLocation().getTownOrProvince(), house.getLocation().getAddress(), house.getLocation().getRegion()));
                    h.setHouseRating(rating.getRating(house.getFeedbacks()));
                });
                profileAdminResponse.setHouseResponseForAdminUsers(houseResponseForAdminUsers);
            } else if (bookingOrAnnouncement != null && bookingOrAnnouncement.equals("My announcement")) {
                List<HouseResponseForAdminUsers> houseResponseForAdminUsers = userRepository.getUserByAnnouncement(userId);
                houseResponseForAdminUsers.forEach(h -> {
                    House house = houseRepository.findById(h.getId()).orElseThrow(() -> new NotFoundException("House not found!"));
                    h.setImages(houseRepository.findImagesByHouseId(house.getId()));
                    h.setLocationResponse(new LocationResponse(house.getLocation().getId(), house.getLocation().getTownOrProvince(), house.getLocation().getAddress(), house.getLocation().getRegion()));
                    h.setHouseRating(rating.getRating(house.getFeedbacks()));
                });
                profileAdminResponse.setHouseResponseForAdminUsers(houseResponseForAdminUsers);
            }
            log.info("Shoe admin profile");
            return profileAdminResponse;
        }
        throw new BadRequestException("Invalid request!!!");
    }

    @Override
    public SimpleResponse allBlocked(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found!"));
        user.getAnnouncements().forEach(h -> h.setHousesStatus(HousesStatus.BLOCKED));
        userRepository.save(user);
        emailService.sendMessage(user.getEmail(), "%s, new message from airbnb" + user.getName(), "Your all houses are blocked!");
        log.info("All Houses are successfully blocked");
        return new SimpleResponse("All Houses are successfully blocked :)");
    }

    private ProfileResponse houseType(String sortHousesByApartments, String sortHousesByHouses, String sortHousesAsDesired, Long userId) {
        ProfileResponse profileResponse = new ProfileResponse(userId, userRepository.findById(userId).get().getName(), userRepository.findById(userId).get().getEmail());
        int counter = 0;
        if (sortHousesByApartments.equals("Apartment")) {
            for (House house : userRepository.findById(userId).get().getAnnouncements()) {
                if (house.getHouseType().toString().equals("APARTMENT")) {
                    ProfileHouseResponse profileHouseResponse = houseResponseConverter.view(house);
                    profileHouseResponse.setCountOfBooking(house.getBookings());
                    profileHouseResponse.setCountOfFavorite((long) favoriteHouseRepository.getCountOfFavorite(house));
                    if (house.getHousesStatus().equals(HousesStatus.BLOCKED)) {
                        profileHouseResponse.setIsBlockCed(true);
                    }
                    profileResponse.addProfileHouseResponse(profileHouseResponse);
                }
            }
            counter++;
        }
        if (sortHousesByHouses.equals("House")) {
            for (House house : userRepository.findById(userId).get().getAnnouncements()) {
                if (house.getHouseType().toString().equals("HOUSE")) {
                    ProfileHouseResponse profileHouseResponse = houseResponseConverter.view(house);
                    profileHouseResponse.setCountOfBooking(house.getBookings());
                    profileHouseResponse.setCountOfFavorite((long) favoriteHouseRepository.getCountOfFavorite(house));
                    if (house.getHousesStatus().equals(HousesStatus.BLOCKED)) {
                        profileHouseResponse.setIsBlockCed(true);
                    }
                    profileResponse.addProfileHouseResponse(profileHouseResponse);
                }
            }
            counter++;
        }
        if (counter == 0) {
            for (House house : userRepository.findById(userId).get().getAnnouncements()) {
                ProfileHouseResponse profileHouseResponse = houseResponseConverter.view(house);
                profileHouseResponse.setCountOfBooking(house.getBookings());
                profileHouseResponse.setCountOfFavorite((long) favoriteHouseRepository.getCountOfFavorite(house));
                if (house.getHousesStatus().equals(HousesStatus.BLOCKED)) {
                    profileHouseResponse.setIsBlockCed(true);
                }
                profileResponse.addProfileHouseResponse(profileHouseResponse);
                if (sortHousesAsDesired.equals("In wish list")) {
                    profileResponse.getProfileHouseResponses().sort(Comparator.comparing(ProfileHouseResponse::getRating).reversed());
                }
            }
            return profileResponse;
        }
        if (sortHousesAsDesired.equals("In wish list")) {
            profileResponse.getProfileHouseResponses().sort(Comparator.comparing(ProfileHouseResponse::getRating).reversed());
        }
        log.info("Show profile");
        return profileResponse;
    }

    private ProfileResponse price(String sortHousesByApartments, String sortHousesByHouses, String sortHousesAsDesired, String sortingHousesByValue, Long userId) {
        ProfileResponse profileResponse = houseType(sortHousesByApartments, sortHousesByHouses, sortHousesAsDesired, userId);
        switch (sortingHousesByValue) {
            case "Low to high" -> {
                profileResponse.getProfileHouseResponses().sort(Comparator.comparing(ProfileHouseResponse::getPrice));
            }
            case "High to low" -> {
                profileResponse.getProfileHouseResponses().sort(Comparator.comparing(ProfileHouseResponse::getPrice).reversed());
            }
            default -> {
                log.info("Show profile");
                return profileResponse;
            }
        }
        log.info("Show profile");
        return profileResponse;
    }

    private ProfileResponse star(String sortHousesAsDesired, String sortHousesByApartments, String sortHousesByHouses, String sortingHousesByValue, String sortingHousesByRating, Long userId) {
        ProfileResponse profileResponse1 = price(sortHousesByApartments, sortHousesByHouses, sortHousesAsDesired, sortingHousesByValue, userId);
        ProfileResponse profileResponse = new ProfileResponse(profileResponse1.getId(), profileResponse1.getProfileName(), profileResponse1.getProfileContact());
        if (profileResponse1.getProfileHouseResponses() == null) return profileResponse1;
        switch (sortingHousesByRating) {
            case "One" -> {
                for (ProfileHouseResponse house : profileResponse1.getProfileHouseResponses()) {
                    if (rating.getRating(feedbackRepository.getAllFeedbackByHouseId(house.getId())) >= 0 && rating.getRating(feedbackRepository.getAllFeedbackByHouseId(house.getId())) <= 1) {
                        profileResponse.addProfileHouseResponse(house);
                    }
                }
                log.info("Show profile");
                return profileResponse;
            }
            case "Two" -> {
                for (ProfileHouseResponse house : profileResponse1.getProfileHouseResponses()) {
                    if (rating.getRating(feedbackRepository.getAllFeedbackByHouseId(house.getId())) > 1 && rating.getRating(feedbackRepository.getAllFeedbackByHouseId(house.getId())) <= 2) {
                        profileResponse.addProfileHouseResponse(house);
                    }
                }
                log.info("Show profile");
                return profileResponse;
            }
            case "Three" -> {
                for (ProfileHouseResponse house : profileResponse1.getProfileHouseResponses()) {
                    if (rating.getRating(feedbackRepository.getAllFeedbackByHouseId(house.getId())) > 2 && rating.getRating(feedbackRepository.getAllFeedbackByHouseId(house.getId())) <= 3) {
                        profileResponse.addProfileHouseResponse(house);
                    }
                }
                log.info("Show profile");
                return profileResponse;
            }
            case "Four" -> {
                for (ProfileHouseResponse house : profileResponse1.getProfileHouseResponses()) {
                    if (rating.getRating(feedbackRepository.getAllFeedbackByHouseId(house.getId())) > 3 && rating.getRating(feedbackRepository.getAllFeedbackByHouseId(house.getId())) <= 4) {
                        profileResponse.addProfileHouseResponse(house);
                    }
                }
                log.info("Show profile");
                return profileResponse;
            }
            case "Five" -> {
                for (ProfileHouseResponse house : profileResponse1.getProfileHouseResponses()) {
                    if (rating.getRating(feedbackRepository.getAllFeedbackByHouseId(house.getId())) > 4 && rating.getRating(feedbackRepository.getAllFeedbackByHouseId(house.getId())) <= 5) {
                        profileResponse.addProfileHouseResponse(house);
                    }
                }
                log.info("Show profile");
                return profileResponse;
            }
            default -> {
                log.info("Show profile");
                return profileResponse1;
            }
        }
    }

    private List<ProfileHouseResponse> getProfileHouseResponse(int page, int size, List<ProfileHouseResponse> profileHouseResponses) {
        int startItem = (page - 1) * size;
        List<ProfileHouseResponse> list;

        if (profileHouseResponses.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + size, profileHouseResponses.size());
            list = profileHouseResponses.subList(startItem, toIndex);
        }
        log.info("Show profile house");
        return list;
    }


}