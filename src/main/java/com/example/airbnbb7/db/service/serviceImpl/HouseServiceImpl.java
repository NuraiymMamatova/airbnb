package com.example.airbnbb7.db.service.serviceImpl;

import com.example.airbnbb7.converter.request.HouseRequestConverter;
import com.example.airbnbb7.converter.response.HouseResponseConverter;
import com.example.airbnbb7.db.customClass.Rating;
import com.example.airbnbb7.db.entities.Booking;
import com.example.airbnbb7.db.entities.House;
import com.example.airbnbb7.db.entities.Location;
import com.example.airbnbb7.db.entities.User;
import com.example.airbnbb7.db.enums.HouseType;
import com.example.airbnbb7.db.enums.HousesStatus;
import com.example.airbnbb7.db.repository.*;
import com.example.airbnbb7.db.service.AnnouncementService;
import com.example.airbnbb7.db.service.HouseService;
import com.example.airbnbb7.dto.request.HouseRequest;
import com.example.airbnbb7.dto.response.*;
import com.example.airbnbb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService {

    private final BookingRepository bookingRepository;

    private final RoleRepository roleRepository;

    private final HouseRepository houseRepository;

    private final HouseRequestConverter houseRequestConverter;

    private final HouseResponseConverter houseResponseConverter;

    private final UserRepository userRepository;

    private final LocationRepository locationRepository;

    private final Rating rating;

    private final FeedbackRepository feedbackRepository;

    @Override
    public HouseResponse deleteByIdHouse(Long houseId) {
        House house = houseRepository.findById(houseId).orElseThrow(() -> new NotFoundException("House id not found"));
        houseRepository.delete(house);
        return houseResponseConverter.viewHouse(house);
    }

    @Override
    public HouseResponse updateHouse(Long id, HouseRequest houseRequest) {
        House house = houseRepository.findById(id).orElseThrow(() -> new NotFoundException("House id not found"));
        houseRequestConverter.update(house, houseRequest);
        return houseResponseConverter.viewHouse(houseRepository.save(house));
    }

    @Override
    public HouseResponse save(HouseRequest houseRequest, User user) {
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
        return houseResponse;
    }

    @Override
    public List<HouseResponseSortedPagination> getAllPagination(HouseType houseType, String fieldToSort, String nameOfHouse, int page, int countOfHouses, String priceSort, String region) {
        Pageable pageable = PageRequest.of(page - 1, countOfHouses);
        String text;
        if (nameOfHouse == null)
            text = "";
        else text = nameOfHouse;
        List<House> houses = houseRepository.findAll();
        List<HouseResponseSortedPagination> houseResponses = houseRepository.pagination(text, pageable);
        List<HouseResponseSortedPagination> sortedHouseResponse = sort(pageable, houseType, region, priceSort, fieldToSort, houseResponses);
        for (HouseResponseSortedPagination response : sortedHouseResponse) {
            Long index = response.getId() - 1;
            if (index.equals(houses.get(Math.toIntExact(index)).getLocation().getId())) {
                response.setImages(houses.get(Math.toIntExact(index)).getImages());
                response.setLocationResponse(locationRepository.convertToResponse(houses.get(Math.toIntExact(index)).getLocation()));
                response.setHouseRating(rating.getRating(feedbackRepository.getAllFeedbackByHouseId(response.getId())));
            } else {
                Location location = locationRepository.findById(response.getId()).orElseThrow(() -> new NotFoundException("location not found!"));
                response.setImages(houses.get(Math.toIntExact(index)).getImages());
                response.setLocationResponse(locationRepository.convertToResponse(location));
                response.setHouseRating(rating.getRating(feedbackRepository.getAllFeedbackByHouseId(response.getId())));
            }
        }
        return sortedHouseResponse;
    }

    public List<HouseResponseSortedPagination> sort(Pageable pageable, HouseType houseType, String region, String priceSort, String fieldToSort, List<HouseResponseSortedPagination> sortedHouseResponse) {
        switch (fieldToSort) {
            case "homeType":
                return switch (houseType) {
                    case HOUSE -> houseRepository.getAllHouses(pageable);
                    case APARTMENT -> houseRepository.getAllApartments(pageable);
                };
            case "homePrice":
                if (priceSort.equals("High to low")) {
                    sortedHouseResponse.sort(Comparator.comparing(HouseResponseSortedPagination::getPrice).reversed());
                } else if (priceSort.equals("Low to high")) {
                    sortedHouseResponse.sort(Comparator.comparing(HouseResponseSortedPagination::getPrice));
                }
                break;
            case "region":
                switch (region) {
                    case "Bishkek":
                        return houseRepository.regionHouses("Bishkek", pageable);
                    case "Osh":
                        return houseRepository.regionHouses("Osh", pageable);
                    case "Batken":
                        return houseRepository.regionHouses("Batken", pageable);
                    case "Talas":
                        return houseRepository.regionHouses("Talas", pageable);
                    case "Naryn":
                        return houseRepository.regionHouses("Naryn", pageable);
                    case "Chui":
                        return houseRepository.regionHouses("Chui", pageable);
                    case "Issyk-Kul":
                        return houseRepository.regionHouses("Issyk-Kul", pageable);
                    case "Jalal-Abat":
                        return houseRepository.regionHouses("Jalal-Abat", pageable);
                }
        }
        return sortedHouseResponse;
    }


    @Override
    public AnnouncementService getAnnouncementById(Long houseId, Long userId) {
        AnnouncementResponseForUser house = houseRepository.findHouseByIdForUser(houseId).orElseThrow(() -> new NotFoundException("House not found!"));
        UserResponse userResponse = userRepository.findUserById(houseRepository.findById(houseId).orElseThrow(() -> new NotFoundException("User not found!")).getOwner().getId());
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
        if (userResponse.getId() == userId) {
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
            announcementResponseForAdmin.setOwner(userResponse);
            announcementResponseForAdmin.setRating(rating.getRatingCount(feedbackRepository.getAllFeedbackByHouseId(houseId)));
            return announcementResponseForAdmin;
        }
        house.setOwner(userResponse);
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
}
