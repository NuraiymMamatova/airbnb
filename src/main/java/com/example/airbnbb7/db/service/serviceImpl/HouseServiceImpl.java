package com.example.airbnbb7.db.service.serviceImpl;

import com.example.airbnbb7.converter.request.HouseRequestConverter;
import com.example.airbnbb7.db.customClass.Rating;
import com.example.airbnbb7.db.customClass.SimpleResponse;
import com.example.airbnbb7.db.entities.*;
import com.example.airbnbb7.db.enums.HouseType;
import com.example.airbnbb7.db.enums.HousesBooked;
import com.example.airbnbb7.db.enums.HousesStatus;
import com.example.airbnbb7.db.repository.*;
import com.example.airbnbb7.db.service.AnnouncementService;
import com.example.airbnbb7.db.service.EmailService;
import com.example.airbnbb7.db.service.HouseService;
import com.example.airbnbb7.dto.request.HouseRequest;
import com.example.airbnbb7.dto.response.*;
import com.example.airbnbb7.exceptions.BadRequestException;
import com.example.airbnbb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService {

    private final BookingRepository bookingRepository;

    private final RoleRepository roleRepository;

    private final HouseRepository houseRepository;

    private final HouseRequestConverter houseRequestConverter;

    private final UserRepository userRepository;

    private final LocationRepository locationRepository;

    private final Rating rating;

    private final FeedbackRepository feedbackRepository;

    private final FavoriteHouseRepository favoriteHouseRepository;

    private final EmailService emailService;

    private Long countOfRegion;

    @Override
    public SimpleResponse deleteByIdHouse(Long houseId, Authentication authentication) {
        if (authentication != null) {
            User user = (User) authentication.getPrincipal();
            House house = houseRepository.findById(houseId).orElseThrow(() -> new NotFoundException("House id not found"));
            if (house.getOwner().getId() == user.getId() || roleRepository.findRoleByUserId(user.getId()).getId() == 1) {
                favoriteHouseRepository.deleteAll(favoriteHouseRepository.getAllFavoriteHouseByHouseId(houseId));
                houseRepository.delete(house);
            } else {
                throw new BadCredentialsException("You can't delete this announcement because it's not your announcement");
            }
            return new SimpleResponse("House successfully deleted");
        }
        throw new BadRequestException("Authentication cannot be null!");

    }

    @Override
    public SimpleResponse updateHouse(Long id, Authentication authentication, HouseRequest houseRequest) {
        if (authentication != null) {
            User user = (User) authentication.getPrincipal();
            House house = houseRepository.findById(id).orElseThrow(() -> new NotFoundException("House id not found"));
            if (house.getOwner().getId() == user.getId()) {
                house.setHousesStatus(HousesStatus.ON_MODERATION);
                houseRequestConverter.update(house, houseRequest);
                houseRepository.save(house);
            } else {
                throw new BadCredentialsException("You can't update this announcement because it's not your announcement");
            }
            return new SimpleResponse("House successfully updated!");
        }
        throw new BadRequestException("Authentication cannot be null!");
    }

    @Override
    public SimpleResponse save(HouseRequest houseRequest, Authentication authentication) {
        if (authentication != null) {
            User user = (User) authentication.getPrincipal();
            House house = new House(houseRequest.getPrice(), houseRequest.getTitle(), houseRequest.getDescriptionOfListing(), houseRequest.getMaxOfGuests(), houseRequest.getImages(), houseRequest.getHouseType());
            if (houseRequest.getLocation() != null) {
                Location location = new Location(houseRequest.getLocation().getAddress(), houseRequest.getLocation().getTownOrProvince(), houseRequest.getLocation().getRegion());
                location.setHouse(house);
                house.setLocation(location);
                house.setDateHouseCreated(LocalDate.now());
                house.setHousesStatus(HousesStatus.ON_MODERATION);
                house.setHousesBooked(HousesBooked.NOT_BOOKED);
                house.setBookings(0L);
                house.setOwner(user);
                houseRepository.save(house);

                HouseResponse houseResponse = new HouseResponse(house.getId(), house.getPrice(), house.getTitle(),
                        house.getDescriptionOfListing(), house.getMaxOfGuests(), house.getHouseType());
                houseResponse.setOwner(new UserResponse(house.getOwner().getId(), house.getOwner().getName(),
                        house.getOwner().getEmail(), house.getOwner().getImage()));
                houseResponse.setLocation(new LocationResponse(house.getLocation().getId(), house.getLocation().getTownOrProvince(),
                        house.getLocation().getAddress(), house.getLocation().getRegion()));
                houseResponse.setImages(house.getImages());
            } else {
                throw new BadRequestException("Location cannot be null!");
            }
        } else {
            throw new BadRequestException("Authentication cannot be null!");
        }
        return new SimpleResponse("House successfully saved!");
    }

    @Override
    public ApplicationResponse getAllPagination(String search, String region, String popularOrTheLatest, String homeType, String price, Long page, Long pageSize, double userLatitude, double userLongitude) {
        ApplicationResponse applicationResponse = new ApplicationResponse();
        applicationResponse.setPage(page);
        double[] coordinates = {userLatitude, userLongitude};
        int sizePage = (int) Math.ceil((double) sortPrice(region, popularOrTheLatest, homeType, price, coordinates).size() / pageSize);
        applicationResponse.setPageSize((long) sizePage);
        if (search != null) {
            List<HouseResponseSortedPagination> houseResponseSortedPaginations = new ArrayList<>(getAllPagination(page, pageSize, houseRepository.searchByQuery(search)));

            houseResponseSortedPaginations.forEach(h -> {
                House house = houseRepository.findById(h.getId()).orElseThrow(() -> new NotFoundException("House not found!"));
                h.setImages(house.getImages());
                h.setLocationResponse(locationRepository.findLocationByHouseId(house.getId()).orElseThrow(() -> new NotFoundException("Location not found!")));
                h.setHouseRating(rating.getRating(house.getFeedbacks()));
            });
            applicationResponse.setPaginationList(houseResponseSortedPaginations);
            return applicationResponse;
        } else {
            applicationResponse.setPaginationList(getAllPagination(page, pageSize, sortPrice(region, popularOrTheLatest, homeType, price, coordinates)));
            applicationResponse.setCountOfRegion(countOfRegion);
            return applicationResponse;
        }
    }

    @Override
    public AnnouncementService getAnnouncementById(Long houseId, Authentication authentication) {
        AnnouncementResponseForUser house = houseRepository.findHouseByIdForUser(houseId).orElseThrow(() -> new NotFoundException("House not found!"));
        UserResponse userResponse = userRepository.findUserById(houseRepository.findById(houseId).orElseThrow(() -> new NotFoundException("User not found!")).getOwner().getId());
        User user = null;
        if (authentication != null) {
            user = (User) authentication.getPrincipal();
        }
        house.setImages(houseRepository.findImagesByHouseId(houseId));
        house.setLocation(locationRepository.findLocationByHouseId(houseId).orElseThrow(() -> new NotFoundException("Location not found!")));
        List<FeedbackResponse> feedbackResponses = new ArrayList<>();
        List<Feedback> feedbacks = feedbackRepository.getAllFeedbackByHouseId(houseId);
        for (Feedback feedback : feedbacks) {
            FeedbackResponse feedbackResponse = feedbackRepository.findFeedbackByFeedbackId(feedback.getId());
            feedbackResponse.setOwner(feedbackRepository.findOwnerFeedbackByFeedbackId(feedback.getId()));
            feedbackResponses.add(feedbackResponse);
        }
        house.setFeedbacks(feedbackResponses);
        house.setRating(rating.getRatingCount(feedbacks));
        if (user != null) {
            List<Booking> bookings = bookingRepository.getBookingsByUserId(user.getId());
            house.setRating(rating.getRatingCount(feedbacks));
            for (Booking booking : bookings) {
                if (booking.getHouse().getId() == houseId) {
                    house.setBookingResponse(bookingRepository.findBookingById(booking.getId()).orElseThrow(() -> new NotFoundException("Location not found!")));
                }

            }
            if (userResponse.getId() == user.getId()) {
                AnnouncementResponseForVendor houseResponseForVendor = houseRepository.findHouseByIdForVendor(houseId).orElseThrow(() -> new NotFoundException("House not found!"));
                houseResponseForVendor.setImages(houseRepository.findImagesByHouseId(houseId));
                List<BookingResponse> bookingResponses = new ArrayList<>();
                for (BookingResponse booking : bookingRepository.getBookingsByHouseId(houseId)) {
                    booking.setOwner(userRepository.findUserById(bookingRepository.getUserIdByBookingId(booking.getId())));
                    bookingResponses.add(booking);
                }
                houseResponseForVendor.setRating(rating.getRatingCount(feedbacks));
                houseResponseForVendor.setBookingResponses(bookingResponses);
                houseResponseForVendor.setFeedbacks(feedbackResponses);
                houseResponseForVendor.setInFavorites(userRepository.inFavorite(houseId));
                houseResponseForVendor.setLocation(locationRepository.findLocationByHouseId(houseId).orElseThrow(() -> new NotFoundException("Location not found!")));
                return houseResponseForVendor;
            } else if (roleRepository.findRoleByUserId(user.getId()).getNameOfRole().equals("ADMIN")) {
                AnnouncementResponseForAdmin announcementResponseForAdmin = houseRepository.findHouseByIdForAdmin(houseId).orElseThrow(() -> new NotFoundException("House not found!"));
                House hous = houseRepository.findById(houseId).orElseThrow(() -> new NotFoundException("House not found!"));
                hous.setWatchedOrNot(true);
                houseRepository.save(hous);
                announcementResponseForAdmin.setImages(houseRepository.findImagesByHouseId(houseId));
                announcementResponseForAdmin.setLocation(locationRepository.findLocationByHouseId(houseId).orElseThrow(() -> new NotFoundException("Location not found!")));
                announcementResponseForAdmin.setFeedbacks(feedbackResponses);
                announcementResponseForAdmin.setOwner(userResponse);
                announcementResponseForAdmin.setRating(rating.getRatingCount(feedbacks));
                return announcementResponseForAdmin;
            }
        }
        house.setOwner(userResponse);
        return house;
    }

    @Override
    public SimpleResponse changeStatusOfHouse(Long houseId, String message, HousesStatus housesStatus) {
        House house = houseRepository.findById(houseId).orElseThrow(() -> new NotFoundException("House not found!"));
        switch (housesStatus) {
            case ACCEPT -> {
                house.setHousesStatus(HousesStatus.ACCEPT);
                houseRepository.save(house);
                emailService.sendMessage(house.getOwner().getEmail(), String.format("House with title %s accepted :)", house.getTitle()), "Moderation successfully passed!");
                return new SimpleResponse("Accepted :)");
            }
            case REJECT -> {
                house.setHousesStatus(HousesStatus.REJECT);
                houseRepository.save(house);
                if (message != null) {
                    emailService.sendMessage(house.getOwner().getEmail(), String.format("House with title %s rejected :(", house.getTitle()), message);
                } else {
                    throw new BadRequestException("Message cannot be null!");
                }
                return new SimpleResponse("Successfully sent :)");
            }
            case BLOCKED -> {
                if (house.getHousesStatus().equals(HousesStatus.BLOCKED)) {
                    house.setHousesStatus(HousesStatus.ON_MODERATION);
                    houseRepository.save(house);
                    emailService.sendMessage(house.getOwner().getEmail(), String.format("House with title %s unblocked :)", house.getTitle()), "House unblocked :)");
                    return new SimpleResponse("Unblocked :)");
                } else {
                    house.setHousesStatus(HousesStatus.BLOCKED);
                    houseRepository.save(house);
                    emailService.sendMessage(house.getOwner().getEmail(), String.format("House with title %s blocked :(", house.getTitle()), "House blocked :(");
                    return new SimpleResponse("Blocked :)");
                }
            }
        }
        return new SimpleResponse();
    }

    @Override
    public ApplicationResponseForAdmin getAllStatusOfTheWholeHouseOnModeration(Long page, Long pageSize) {
        List<HouseResponseForAdmin> houseResponseForAdmins = houseRepository.getAllStatusOfTheWholeHouseOnModeration();
        int sizePage = (int) Math.ceil((double) houseResponseForAdmins.size() / pageSize);
        houseResponseForAdmins.forEach(h -> {
            House house = houseRepository.findById(h.getId()).orElseThrow(() -> new NotFoundException("House not found!"));
            Location location = house.getLocation();
            h.setLocationResponse(new LocationResponse(location.getId(), location.getTownOrProvince(),
                    location.getAddress(), location.getRegion()));
            h.setHouseRating(rating.getRating(house.getFeedbacks()));
        });
        return new ApplicationResponseForAdmin(getProfileHouseResponse(page, pageSize, houseResponseForAdmins), page, sizePage);
    }

    @Override
    public List<HouseResponseSortedPagination> getAllHousing(HousesBooked housesBooked, String houseType, String price, String popularOrTheLatest) {
        List<House> allHouses = houseRepository.findAll();
        List<HouseResponseSortedPagination> sortHouses = new ArrayList<>();
        for (HouseResponseSortedPagination houseResponse : sortPrice(null, popularOrTheLatest, houseType, price, null)) {
            for (House entityHouse : allHouses) {
                if (housesBooked != null) {
                    if (entityHouse.getId() == houseResponse.getId() && entityHouse.getHousesBooked().equals(housesBooked) && entityHouse.getHousesStatus().equals(HousesStatus.ACCEPT)) {
                        houseResponse.setImages(entityHouse.getImages());
                        houseResponse.setLocationResponse(locationRepository.findLocationByHouseId(entityHouse.getId()).orElseThrow(() -> new NotFoundException("Location not found!")));
                        houseResponse.setHouseRating(rating.getRating(feedbackRepository.getAllFeedbackByHouseId(entityHouse.getId())));
                        sortHouses.add(houseResponse);
                    }
                } else if (entityHouse.getId() == houseResponse.getId() && entityHouse.getHousesStatus().equals(HousesStatus.ACCEPT)) {
                    houseResponse.setImages(entityHouse.getImages());
                    houseResponse.setLocationResponse(locationRepository.findLocationByHouseId(entityHouse.getId()).orElseThrow(() -> new NotFoundException("Location not found!")));
                    houseResponse.setHouseRating(rating.getRating(feedbackRepository.getAllFeedbackByHouseId(entityHouse.getId())));
                    sortHouses.add(houseResponse);
                }
            }
        }
        return sortHouses;
    }

    @Override
    public List<AccommodationResponse> getLatestAccommodation(boolean popularHouse, boolean popularApartments) {

        if (popularHouse) {
            List<AccommodationResponse> houseResponses = houseRepository.getPopularHouse();
            for (AccommodationResponse accommodationResponse : houseResponses) {
                accommodationResponse.setLocationResponse(locationRepository.findLocationByHouseId(accommodationResponse.getId()).orElseThrow(() -> new NotFoundException("Location not found!")));
                accommodationResponse.setImages(houseRepository.findImagesByHouseId(accommodationResponse.getId()));
                accommodationResponse.setRating(rating.getRating(feedbackRepository.getAllFeedbackByHouseId(accommodationResponse.getId())));
            }
            houseResponses.sort(Comparator.comparing(AccommodationResponse::getRating).reversed());
            return houseResponses.stream().limit(3).toList();
        }
        if (popularApartments) {
            List<AccommodationResponse> popularApartmentByCountOfBookedUser = houseRepository.getPopularApartment();
            for (AccommodationResponse accommodationResponse : popularApartmentByCountOfBookedUser) {
                accommodationResponse.setLocationResponse(locationRepository.findLocationByHouseId(accommodationResponse.getId()).orElseThrow(() -> new NotFoundException("Location not found!")));
                accommodationResponse.setImages(houseRepository.findImagesByHouseId(accommodationResponse.getId()));
                accommodationResponse.setRating(rating.getRating(feedbackRepository.getAllFeedbackByHouseId(accommodationResponse.getId())));
            }
            popularApartmentByCountOfBookedUser.sort(Comparator.comparing(AccommodationResponse::getRating).reversed());
            return popularApartmentByCountOfBookedUser.stream().limit(7).toList();
        }
        List<AccommodationResponse> houseResponses = houseRepository.getLatestAccommodation();
        for (AccommodationResponse houseResponse : houseResponses) {
            House house = houseRepository.findById(houseResponse.getId()).orElseThrow(() -> new NotFoundException("House Id not found"));
            houseResponse.setImages(house.getImages());
            houseResponse.setLocationResponse(locationRepository.convertToResponse(house.getLocation()));
        }
        return houseResponses.stream().limit(7).toList();
    }

    @Override
    public List<HouseResponseSortedPagination> searchNearby(double userLatitude, double userLongitude) {
        List<HouseResponseSortedPagination> nearbyHouses = new ArrayList<>();
        for (HouseResponseSortedPagination house : houseRepository.getAllResponse()) {
            LocationResponse locationResponse = locationRepository.findLocationByHouseId(house.getId()).orElseThrow(() -> new NotFoundException("Location is not found!"));
            double[] coordinates = getCoordinates(locationResponse.getAddress());
            double distance = distance(userLatitude, userLongitude, coordinates[0], coordinates[1]);
            if (distance <= 100) {
                house.setImages(houseRepository.findImagesByHouseId(house.getId()));
                house.setLocationResponse(locationResponse);
                nearbyHouses.add(house);
            }
        }
        return nearbyHouses;
    }

    private List<HouseResponseSortedPagination> sortRegion(String region, double[] coordinates) {
        if (region == null) region = "All";
        List<HouseResponseSortedPagination> houseResponseSortedPaginations = new ArrayList<>();
        List<HouseResponseSortedPagination> houseResponses = new ArrayList<>();
        if (coordinates[0] == 0 && coordinates[1] == 0) {
            for (HouseResponseSortedPagination houses : houseRepository.getAllResponse()) {
                for (House house : houseRepository.findAllAnnouncements()) {
                    if (house.getId() == houses.getId()) {
                        houseResponseSortedPaginations.add(convertHouseToHouseResponseSortedPagination(house));
                    }
                }
            }
        } else {
            for (HouseResponseSortedPagination houseResponseSortedPagination : houseRepository.getAllResponse()) {
                houseResponseSortedPagination.setLocationResponse(locationRepository.findLocationByHouseId(houseResponseSortedPagination.getId()).orElseThrow(() -> new NotFoundException("Location not found!")));
                houseResponseSortedPagination.setImages(houseRepository.findImagesByHouseId(houseResponseSortedPagination.getId()));
                houseResponseSortedPaginations.add(houseResponseSortedPagination);
            }
            List<HouseResponseSortedPagination> allNearbyHouses = getAllNearbyHouses(houseResponseSortedPaginations, searchNearby(coordinates[0], coordinates[1]));
            houseResponseSortedPaginations.addAll(allNearbyHouses);
        }
        if (region.equals("All")) {
            houseResponses.addAll(houseResponseSortedPaginations);
            return houseResponses;
        }
        for (HouseResponseSortedPagination house : houseResponseSortedPaginations) {
            if (house.getLocationResponse().getRegion().equals(region)) {
                houseResponses.add(house);
                this.countOfRegion = (long) houseResponses.size();
            }
        }
        if (houseResponses == null) {
            houseResponses.addAll(houseResponseSortedPaginations);
        }
        return houseResponses;
    }

    private List<HouseResponseSortedPagination> sortPopularOrTheLatest(String region, String popularOrTheLatest, double[] coordinates) {
        List<HouseResponseSortedPagination> houseResponseSortedPaginations = new ArrayList<>(sortRegion(region, coordinates));
        List<HouseResponseSortedPagination> houses = new ArrayList<>();
        if (popularOrTheLatest == null) popularOrTheLatest = "All";
        if (popularOrTheLatest.equals("All")) return houseResponseSortedPaginations;
        if (popularOrTheLatest.equals("Popular")) {
            for (House house : houseRepository.getPopular()) {
                for (HouseResponseSortedPagination houseSorted : houseResponseSortedPaginations) {
                    if (house.getId() == houseSorted.getId()) {
                        houses.add(houseSorted);
                    }
                }
            }
            houses.sort(Comparator.comparing(HouseResponseSortedPagination::getHouseRating).reversed());
            return houses;
        } else if (popularOrTheLatest.equals("The latest")) {
            List<House> houseList = new ArrayList<>();
            for (House house : houseRepository.findAllAnnouncements()) {
                for (HouseResponseSortedPagination houseSorted : houseResponseSortedPaginations) {
                    if (house.getId() == houseSorted.getId()) {
                        houseList.add(house);
                    }
                }
            }
            try {
                houseList.sort(Comparator.comparing(House::getDateHouseCreated).reversed());
            } catch (Exception e) {

            }
            for (House house : houseList) {
                houses.add(convertHouseToHouseResponseSortedPagination(house));
            }
        }
        return houses;
    }

    private HouseResponseSortedPagination convertHouseToHouseResponseSortedPagination(House house) {
        HouseResponseSortedPagination houseResponseSortedPagination = houseRepository.findHouseById(house.getId()).orElseThrow(() -> new NotFoundException("House not found!"));
        houseResponseSortedPagination.setLocationResponse(locationRepository.findLocationByHouseId(house.getId()).orElseThrow(() -> new NotFoundException("Location not found!")));
        houseResponseSortedPagination.setImages(house.getImages());
        houseResponseSortedPagination.setHouseRating(rating.getRating(house.getFeedbacks()));
        return houseResponseSortedPagination;
    }

    private List<HouseResponseSortedPagination> sortHomeType(String region, String popularOrTheLastest, String homeType, double[] coordinates) {
        List<HouseResponseSortedPagination> houseResponseSortedPaginations = new ArrayList<>();
        List<HouseResponseSortedPagination> popularOrTheLatest = sortPopularOrTheLatest(region, popularOrTheLastest, coordinates);
        if (homeType == null) homeType = "All";
        if (homeType.equals("All")) return popularOrTheLatest /*sortPopularOrTheLatest(region, popularOrTheLastest)*/;
        if (homeType.equals("Apartment")) {
            for (HouseResponseSortedPagination house : popularOrTheLatest /*sortPopularOrTheLatest(region, popularOrTheLastest)*/) {
                if (house.getHouseType().equals(HouseType.APARTMENT)) {
                    houseResponseSortedPaginations.add(house);
                }
            }
        } else if (homeType.equals("House")) {
            for (HouseResponseSortedPagination house : popularOrTheLatest/*sortPopularOrTheLatest(region, popularOrTheLastest)*/) {
                if (house.getHouseType().equals(HouseType.HOUSE)) {
                    houseResponseSortedPaginations.add(house);
                }
            }
        }
        return houseResponseSortedPaginations;
    }

    private List<HouseResponseSortedPagination> sortPrice(String region, String popularOrTheLastest, String homeType, String price, double[] coordinates) {
        List<HouseResponseSortedPagination> houseResponseSortedPaginations = new LinkedList<>(sortHomeType(region, popularOrTheLastest, homeType, coordinates));
        if (price == null) price = "All";
        if (price.equals("All")) return houseResponseSortedPaginations;
        if (price.equals("Low to high")) {
            houseResponseSortedPaginations.sort(Comparator.comparing(HouseResponseSortedPagination::getPrice));
        } else if (price.equals("High to low")) {
            houseResponseSortedPaginations.sort(Comparator.comparing(HouseResponseSortedPagination::getPrice).reversed());
        }
        return houseResponseSortedPaginations;
    }

    private List<HouseResponseSortedPagination> getAllPagination(Long page, Long size, List<HouseResponseSortedPagination> houseResponseSortedPaginations) {
        int startItem = (int) ((page - 1) * size);
        List<HouseResponseSortedPagination> list;
        if (houseResponseSortedPaginations.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = (int) Math.min(startItem + size, houseResponseSortedPaginations.size());
            list = houseResponseSortedPaginations.subList(startItem, toIndex);
        }
        return list;
    }

    private List<HouseResponseForAdmin> getProfileHouseResponse(Long page, Long size, List<HouseResponseForAdmin> profileHouseResponses) {
        int startItem = (int) ((page - 1) * size);
        List<HouseResponseForAdmin> list;

        if (profileHouseResponses.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = (int) Math.min(startItem + size, profileHouseResponses.size());
            list = profileHouseResponses.subList(startItem, toIndex);
        }
        return list;
    }

    private List<HouseResponseSortedPagination> getAllNearbyHouses(List<HouseResponseSortedPagination> houseResponseSortedPaginations, List<HouseResponseSortedPagination> nearbyHouses) {
        Set<HouseResponseSortedPagination> houseResponseSortedPaginations1 = new LinkedHashSet<>();
        for (HouseResponseSortedPagination sortedPagination : houseResponseSortedPaginations) {
            for (HouseResponseSortedPagination nearbyHouse : nearbyHouses) {
                if (sortedPagination.getId() == nearbyHouse.getId()) {
                    houseResponseSortedPaginations1.add(sortedPagination);
                }
            }
        }
        for (HouseResponseSortedPagination sortedPagination : houseResponseSortedPaginations) {
            for (HouseResponseSortedPagination nearbyHouse : nearbyHouses) {
                if (sortedPagination.getId() != nearbyHouse.getId()) {
                    houseResponseSortedPaginations1.add(sortedPagination);
                }
            }
        }
        return new LinkedList<>(houseResponseSortedPaginations1);
    }

    private double[] getCoordinates(String address) {
        double latitude = 0;
        double longitude = 0;
        try {
            String urlString = "https://nominatim.openstreetmap.org/search?q=" + URLEncoder.encode(address, StandardCharsets.UTF_8) + "&format=json&addressdetails=1&limit=1";
            URL url = new URL(urlString);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
            reader.close();
            String jsonString = jsonBuilder.toString();
            jsonString = jsonString.substring(jsonString.indexOf("{"));
            JSONObject json = new JSONObject(jsonString.trim());
            latitude = json.getDouble("lat");
            longitude = json.getDouble("lon");
        } catch (Exception e) {
            e.getStackTrace();
        }
        return new double[]{latitude, longitude};
    }

    private double distance(double userLatitude, double userLongitude, double houseLatitude, double houseLongitude) {
        double earthRadius = 6371;
        double dLat = Math.toRadians(houseLatitude - userLatitude);
        double dLon = Math.toRadians(houseLongitude - userLongitude);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(userLatitude)) * Math.cos(Math.toRadians(houseLatitude)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadius * c;
    }

}
