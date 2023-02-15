package com.example.airbnbb7.db.service.serviceImpl;

import com.example.airbnbb7.converter.request.HouseRequestConverter;
import com.example.airbnbb7.db.customClass.Rating;
import com.example.airbnbb7.db.customClass.SimpleResponse;
import com.example.airbnbb7.db.entities.Booking;
import com.example.airbnbb7.db.entities.House;
import com.example.airbnbb7.db.entities.Location;
import com.example.airbnbb7.db.entities.User;
import com.example.airbnbb7.db.enums.HouseType;
import com.example.airbnbb7.db.enums.HousesStatus;
import com.example.airbnbb7.db.repository.*;
import com.example.airbnbb7.db.service.AnnouncementService;
import com.example.airbnbb7.db.service.HouseService;
import com.example.airbnbb7.db.service.UserService;
import com.example.airbnbb7.dto.request.HouseRequest;
import com.example.airbnbb7.dto.response.*;
import com.example.airbnbb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService {

    private final BookingRepository bookingRepository;

    private final RoleRepository roleRepository;

    private final HouseRepository houseRepository;

    private final HouseRequestConverter houseRequestConverter;

    private final UserRepository userRepository;

    private final UserService userService;

    private final LocationRepository locationRepository;

    private final Rating rating;

    private final FeedbackRepository feedbackRepository;

    @Override
    public SimpleResponse deleteByIdHouse(Long houseId) {
        House house = houseRepository.findById(houseId).orElseThrow(() -> new NotFoundException("House id not found"));
        houseRepository.delete(house);
        return new SimpleResponse("House successfully deleted");
    }

    @Override
    public SimpleResponse updateHouse(Long id, HouseRequest houseRequest) {
        House house = houseRepository.findById(id).orElseThrow(() -> new NotFoundException("House id not found"));
        houseRequestConverter.update(house, houseRequest);
        return new SimpleResponse("House successfully updated!");
    }

    @Override
    public SimpleResponse save(HouseRequest houseRequest) {
        User user = userRepository.findByEmail(userService.getEmail()).orElseThrow(() -> new NotFoundException("Email not found"));
        House house = new House(houseRequest.getPrice(), houseRequest.getTitle(), houseRequest.getDescriptionOfListing(), houseRequest.getMaxOfGuests(), houseRequest.getImages(), houseRequest.getHouseType());
        Location location = new Location(houseRequest.getLocation().getAddress(), houseRequest.getLocation().getTownOrProvince(), houseRequest.getLocation().getRegion());
        location.setHouse(house);
        house.setLocation(location);
        house.setDateHouseCreated(LocalDate.now());
        house.setHousesStatus(HousesStatus.ON_MODERATION);
        house.setOwner(user);
        houseRepository.save(house);

        HouseResponse houseResponse = new HouseResponse(house.getId(), house.getPrice(), house.getTitle(),
                house.getDescriptionOfListing(), house.getMaxOfGuests(), house.getHouseType());
        houseResponse.setOwner(new UserResponse(house.getOwner().getId(), house.getOwner().getName(),
                house.getOwner().getEmail(), house.getOwner().getImage()));
        houseResponse.setLocation(new LocationResponse(house.getLocation().getId(), house.getLocation().getTownOrProvince(),
                house.getLocation().getAddress(), house.getLocation().getRegion()));
        houseResponse.setImages(house.getImages());
        return new SimpleResponse("House successfully saved!");
    }

    @Override
    public ApplicationResponse getAllPagination(HouseType houseType, String sorting, String search, int page, int countOfHouses, String region, String popularAndLatest) {
        ApplicationResponse applicationResponse = new ApplicationResponse();
        Pageable pageable = PageRequest.of(page - 1, countOfHouses);
        List<HouseResponseSortedPagination> houseResponses = new ArrayList<>();
        if (search == null) {
            Page<HouseResponseSortedPagination> houseResponsePage = houseRepository.getAllResponse(pageable);
            houseResponses = houseResponsePage.getContent();
            applicationResponse.setPageSize(houseResponsePage.getTotalPages());
            houseResponses.forEach(h -> {
                House house = houseRepository.findById(h.getId()).orElseThrow(() -> new NotFoundException("House not found!"));
                Location location = house.getLocation();
                h.setLocationResponse(new LocationResponse(location.getId(), location.getTownOrProvince(),
                        location.getAddress(), location.getRegion()));
                h.setHouseRating(rating.getRating(house.getFeedbacks()));
            });

            houseResponses = filtering(houseResponses, houseType, region);
            houseResponses.forEach(h -> {
                House house = houseRepository.findById(h.getId()).orElseThrow(() -> new NotFoundException("House not found!"));
                Location location = house.getLocation();
                h.setImages(house.getImages());
                h.setLocationResponse(new LocationResponse(location.getId(), location.getTownOrProvince(),
                        location.getAddress(), location.getRegion()));
                h.setHouseRating(rating.getRating(house.getFeedbacks()));
            });
            houseResponses = sortHouse(houseResponses, sorting);
        } else {
            Page<HouseResponseSortedPagination> houseResponsePage = houseRepository.pagination(search, pageable);
            houseResponses = houseResponsePage.getContent();
            applicationResponse.setPageSize(houseResponsePage.getTotalPages());
        }
        houseResponses.forEach(h -> {
            House house = houseRepository.findById(h.getId()).orElseThrow(() -> new NotFoundException("House not found!"));
            Location location = house.getLocation();
            h.setImages(house.getImages());
            h.setLocationResponse(new LocationResponse(location.getId(), location.getTownOrProvince(),
                    location.getAddress(), location.getRegion()));
            h.setHouseRating(rating.getRating(house.getFeedbacks()));
        });
        applicationResponse.setPage((long) page);
        applicationResponse.setPaginationList(houseResponses);
        applicationResponse.setCountOfRegion(houseRepository.count(region));
        return applicationResponse;
    }

    public List<HouseResponseSortedPagination> sortHouse(List<HouseResponseSortedPagination> houseResponses, String filter) {
        List<HouseResponseSortedPagination> sort = new LinkedList<>(houseResponses);
        if (filter == null || sort.isEmpty()) {
            return houseResponses;
        }
        if ("Low to high".equals(filter)) {
            sort.sort(Comparator.comparing(HouseResponseSortedPagination::getPrice));
        } else if ("High to low".equals(filter)) {
            sort.sort(Comparator.comparing(HouseResponseSortedPagination::getPrice).reversed());
        }

        return sort;
    }

    private List<HouseResponseSortedPagination> filtering(List<HouseResponseSortedPagination> responseSortedPaginationList, HouseType houseType, String region) {
        List<HouseResponseSortedPagination> responses = responseSortedPaginationList;

        if (houseType != null) {
            switch (houseType) {
                case HOUSE -> {
                    responses = responses.stream().filter(x -> x.getHouseType() == HouseType.HOUSE).toList();
                }
                case APARTMENT -> {
                    responses = responses.stream().filter(x -> x.getHouseType() == HouseType.APARTMENT).toList();
                }
            }
        }

        if (region != null) {
            responses = responses.stream().filter(x -> x.getLocationResponse().getRegion().equals(region)).toList();
        }


        return responses;
    }

    @Override
    public AnnouncementService getAnnouncementById(Long houseId) {
        AnnouncementResponseForUser house = houseRepository.findHouseByIdForUser(houseId).orElseThrow(() -> new NotFoundException("House not found!"));
        UserResponse user = userRepository.findUserById(houseRepository.findById(houseId).orElseThrow(() -> new NotFoundException("User not found!")).getOwner().getId());
        Long userId = UserRepository.getUserId();
        house.setImages(houseRepository.findImagesByHouseId(houseId));
        house.setLocation(locationRepository.findLocationByHouseId(houseId).orElseThrow(() -> new NotFoundException("Location not found!")));
        house.setFeedbacks(feedbackRepository.getFeedbacksByHouseId(houseId));
        List<Booking> bookings = bookingRepository.getBookingsByUserId(userId);
        house.setRating(rating.getRatingCount(feedbackRepository.getAllFeedbackByHouseId(houseId)));
        for (Booking booking : bookings) {
            if (booking.getHouse().getId() == houseId) {
                house.setBookingResponse(bookingRepository.findBookingById(booking.getId()).orElseThrow(() -> new NotFoundException("Location not found!")));
            }

        }
        if (user.getId() == userId) {
            AnnouncementResponseForVendor houseResponseForVendor = houseRepository.findHouseByIdForVendor(houseId).orElseThrow(() -> new NotFoundException("House not found!"));
            houseResponseForVendor.setImages(houseRepository.findImagesByHouseId(houseId));
            List<BookingResponse> bookingResponses = new ArrayList<>();
            for (BookingResponse booking : bookingRepository.getBookingsByHouseId(houseId)) {
                booking.setOwner(userRepository.findUserById(bookingRepository.getUserIdByBookingId(booking.getId())));
                bookingResponses.add(booking);
            }
            houseResponseForVendor.setRating(rating.getRatingCount(feedbackRepository.getAllFeedbackByHouseId(houseId)));
            houseResponseForVendor.setBookingResponses(bookingResponses);
            houseResponseForVendor.setFeedbacks(feedbackRepository.getFeedbacksByHouseId(houseId));
            houseResponseForVendor.setInFavorites(userRepository.inFavorite(houseId));
            houseResponseForVendor.setLocation(locationRepository.findLocationByHouseId(houseId).orElseThrow(() -> new NotFoundException("Location not found!")));
            return houseResponseForVendor;
        } else if (roleRepository.findRoleByUserId(userId).getNameOfRole().equals("ADMIN")) {
            AnnouncementResponseForAdmin announcementResponseForAdmin = houseRepository.findHouseByIdForAdmin(houseId).orElseThrow(() -> new NotFoundException("House not found!"));
            announcementResponseForAdmin.setImages(houseRepository.findImagesByHouseId(houseId));
            announcementResponseForAdmin.setLocation(locationRepository.findLocationByHouseId(houseId).orElseThrow(() -> new NotFoundException("Location not found!")));
            announcementResponseForAdmin.setFeedbacks(feedbackRepository.getFeedbacksByHouseId(houseId));
            announcementResponseForAdmin.setOwner(user);
            announcementResponseForAdmin.setRating(rating.getRatingCount(feedbackRepository.getAllFeedbackByHouseId(houseId)));
            return announcementResponseForAdmin;
        }
        house.setOwner(user);
        return house;
    }

    @Override
    public List<HouseResponse> globalSearch(String searchEngine) {
        String[] searchEngines = searchEngine.toUpperCase().split(" ");
        List<HouseResponse> globalHouses = new ArrayList<>();
        List<House> houses = new ArrayList<>();
        for (House house : houseRepository.findAll()) {
            if (searchEngines.length == 1) {
                if (house.getLocation().getRegion().toUpperCase().contains(searchEngines[0])) {
                    if (!houses.contains(house)) {
                        houses.add(house);
                    }
                } else if (house.getHouseType().toString().toUpperCase().contains(searchEngines[0])
                        || house.getTitle().toUpperCase().contains(searchEngines[0]) || house.getMaxOfGuests().toString().toUpperCase().contains(searchEngines[0])) {
                    if (!houses.contains(house)) {
                        houses.add(house);
                    }
                }
            } else if (searchEngines.length == 2) {
                if (house.getLocation().getRegion().toUpperCase().contains(searchEngines[0]) && house.getHouseType().toString().toUpperCase().contains(searchEngines[1])) {
                    if (!houses.contains(house)) {
                        houses.add(house);
                    }
                }
            } else if (searchEngines.length == 3) {
                if (house.getLocation().getRegion().toUpperCase().contains(searchEngines[0]) && house.getHouseType().toString().toUpperCase().contains(searchEngines[1])
                        && house.getTitle().toUpperCase().contains(searchEngines[2])) {
                    if (!houses.contains(house)) {
                        houses.add(house);
                    }
                }
            } else if (searchEngines.length == 4) {
                if (house.getLocation().getRegion().toUpperCase().contains(searchEngines[0]) && house.getHouseType().toString().toUpperCase().contains(searchEngines[1])
                        && house.getTitle().toUpperCase().contains(searchEngines[2]) && house.getMaxOfGuests().toString().toUpperCase().contains(searchEngines[3])) {
                    if (!houses.contains(house)) {
                        houses.add(house);
                    }
                }
            } else if (searchEngines.length > 4) {
                throw new NotFoundException("Тo such house or apartment exists!!!");
            }
        }

        for (House house : houses) {
            HouseResponse houseResponse = new HouseResponse(house.getId(), house.getPrice(),
                    house.getTitle(), house.getDescriptionOfListing(), house.getMaxOfGuests(), house.getHouseType());
            houseResponse.setOwner(new UserResponse(house.getOwner().getId(),
                    house.getOwner().getName(), house.getOwner().getEmail(), house.getOwner().getImage()));
            houseResponse.setLocation(new LocationResponse(house.getLocation().getId(),
                    house.getLocation().getTownOrProvince(), house.getLocation().getAddress(), house.getLocation().getRegion()));
            houseResponse.setImages(house.getImages());
            globalHouses.add(houseResponse);
        }
        return globalHouses;
    }

    @Override
    public List<HouseResponseSortedPagination> searchNearby(double userLatitude, double userLongitude) {
        List<HouseResponseSortedPagination> nearbyHouses = new ArrayList<>();
        for (House house : houseRepository.findAll()) {
            double[] coordinates = getCoordinates(house.getLocation().getAddress());
            double distance = distance(userLatitude, userLongitude, coordinates[0], coordinates[1]);
            if (distance <= 500) {
                nearbyHouses.add(houseRepository.findHouseById(house.getId()).orElseThrow(() -> new NotFoundException("House not found!")));
            }
        }
        return nearbyHouses;
    }

    public double[] getCoordinates(String address) {
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
