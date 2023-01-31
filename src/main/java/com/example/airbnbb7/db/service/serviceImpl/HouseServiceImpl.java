package com.example.airbnbb7.db.service.serviceImpl;

import com.example.airbnbb7.db.customclass.Rating;
import com.example.airbnbb7.db.entities.Booking;
import com.example.airbnbb7.db.entities.Feedback;
import com.example.airbnbb7.db.entities.House;
import com.example.airbnbb7.db.entities.Location;
import com.example.airbnbb7.db.enums.HouseType;
import com.example.airbnbb7.db.repository.*;
import com.example.airbnbb7.db.service.*;
import com.example.airbnbb7.dto.response.*;
import com.example.airbnbb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService {

    private final LocationService locationService;

    private final FeedbackService feedbackService;

    private final UserService userService;

    private final BookingService bookingService;

    private final BookingRepository bookingRepository;

    private final RoleRepository roleRepository;

    private final HouseRepository houseRepository;

    private final LocationRepository locationRepository;

    private final FeedbackRepository feedbackRepository;

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
                response.setHouseRating(getRating(response.getId()));
            } else {
                Location location = locationRepository.findById(response.getId()).orElseThrow(() -> new NotFoundException("location not found!"));
                response.setImages(houses.get(Math.toIntExact(index)).getImages());
                response.setLocationResponse(locationRepository.convertToResponse(location));
                response.setHouseRating(getRating(response.getId()));
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

    public double getRating(Long houseId) {
        List<Feedback> feedbacks = feedbackRepository.getAllFeedbackByHouseId(houseId);
        List<Integer> ratings = new ArrayList<>();
        for (Feedback feedback : feedbacks) {
            ratings.add(feedback.getRating());
        }
        double sum = 0;
        for (Integer rating : ratings) {
            sum += rating;
        }
        sum = sum / ratings.size();
        String.format("%.1f", sum);
        return sum;
    }

    public Rating getRatingCount(Long houseId) {
        List<Feedback> feedbacks = feedbackRepository.getAllFeedbackByHouseId(houseId);
//        int one = 0, two = 0, three = 0, four = 0, five = 0;
        Rating rating = new Rating();
        for (Feedback feedback : feedbacks) {
            switch (feedback.getRating()) {
                case 1 -> rating.setOne(rating.getOne() + 1);
                case 2 -> rating.setTwo(rating.getTwo() + 1);
                case 3 -> rating.setThree(rating.getThree() + 1);
                case 4 -> rating.setFour(rating.getFour() + 1);
                case 5 -> rating.setFive(rating.getFive() + 1);

//                case 1 -> one++;
//                case 2 -> two++;
//                case 3 -> three++;
//                case 4 -> four++;
//                case 5 -> five++;
            }
        }
        rating.setSumOfRating(getRating(houseId));
        return rating;
    }

    @Override
    public AnnouncementService getHouse(Long houseId, Long userId) {
        AnnouncementResponseForUser house = houseRepository.findHouseByIdForUser(houseId).orElseThrow(() -> new NotFoundException("House not found!"));
        UserResponse user = userService.findUserById(houseRepository.findById(houseId).orElseThrow(() -> new NotFoundException("User not found!")).getOwner().getId());
        house.setImages(houseRepository.findImagesByHouseId(houseId));
        house.setLocation(locationService.findLocationByHouseId(houseId));
        house.setFeedbacks(feedbackService.getFeedbacksByHouseId(houseId));
        List<Booking> bookings = bookingRepository.getBookingsByUserId(userId);
        house.setRating(getRatingCount(houseId));
        for (Booking booking : bookings) {
            if (booking.getHouse().getId() == houseId) {
                house.setBookingResponse(bookingRepository.findBookingById(booking.getId()).get());
            }

        }
        if (user.getId() == userId) {
            return getHouseForVendor(houseId);
        } else if (roleRepository.findRoleByUserId(userId).getNameOfRole().equals("ADMIN")) {
            AnnouncementResponseForAdmin announcementResponseForAdmin = houseRepository.findHouseByIdForAdmin(houseId).orElseThrow(() -> new NotFoundException("House not found!"));
            announcementResponseForAdmin.setImages(houseRepository.findImagesByHouseId(houseId));
            announcementResponseForAdmin.setLocation(locationService.findLocationByHouseId(houseId));
            announcementResponseForAdmin.setFeedbacks(feedbackService.getFeedbacksByHouseId(houseId));
            announcementResponseForAdmin.setOwner(user);
            announcementResponseForAdmin.setRating(getRatingCount(houseId));
            return announcementResponseForAdmin;
        }
        house.setOwner(user);
        return house;
    }

    @Override
    public AnnouncementResponseForVendor getHouseForVendor(Long houseId) {
        AnnouncementResponseForVendor houseResponseForVendor = houseRepository.findHouseByIdForVendor(houseId).orElseThrow(() -> new NotFoundException("House not found!"));
        houseResponseForVendor.setImages(houseRepository.findImagesByHouseId(houseId));
        List<BookingResponse> bookingResponses = new ArrayList<>();
        for (BookingResponse booking : bookingService.getBookingsByHouseId(houseId)) {
            booking.setOwner(userService.findUserById(bookingService.getUserIdByBookingId(booking.getId())));
            bookingResponses.add(booking);
        }
        houseResponseForVendor.setRating(getRatingCount(houseId));
        houseResponseForVendor.setBookingResponses(bookingResponses);
        houseResponseForVendor.setFeedbacks(feedbackService.getFeedbacksByHouseId(houseId));
        houseResponseForVendor.setInFavorites(userService.inFavorite(houseId));
        houseResponseForVendor.setLocation(locationService.findLocationByHouseId(houseId));
        return houseResponseForVendor;
    }

}
