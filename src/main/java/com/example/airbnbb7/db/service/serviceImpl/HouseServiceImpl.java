package com.example.airbnbb7.db.service.serviceImpl;

import com.example.airbnbb7.db.entities.Booking;
import com.example.airbnbb7.db.repository.BookingRepository;
import com.example.airbnbb7.db.repository.HouseRepository;
import com.example.airbnbb7.db.repository.RoleRepository;
import com.example.airbnbb7.db.service.*;
import com.example.airbnbb7.dto.response.*;
import com.example.airbnbb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService {

    private final HouseRepository houseRepository;

    private final LocationService locationService;

    private final FeedbackService feedbackService;

    private final UserService userService;

    private final BookingService bookingService;

    private final BookingRepository bookingRepository;

    private final RoleRepository roleRepository;

    @Override
    public AnnouncementService getHouse(Long houseId, Long userId) {
        AnnouncementResponseForUser house = houseRepository.findHouseByIdForUser(houseId).orElseThrow(() -> new NotFoundException("House not found!"));
        UserResponse user = userService.findUserById(houseRepository.findById(houseId).orElseThrow(() -> new NotFoundException("House not found!")).getOwner().getId());
        house.setImages(houseRepository.findImagesByHouseId(houseId));
        house.setLocation(locationService.findLocationByHouseId(houseId));
        house.setFeedbacks(feedbackService.getFeedbacksByHouseId(houseId));
        List<Booking> bookings = bookingRepository.getBookingsByUserId(userId);
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
        houseResponseForVendor.setBookingResponses(bookingResponses);
        houseResponseForVendor.setFeedbacks(feedbackService.getFeedbacksByHouseId(houseId));
        houseResponseForVendor.setInFavorites(userService.inFavorite(houseId));
        houseResponseForVendor.setLocation(locationService.findLocationByHouseId(houseId));
        return houseResponseForVendor;
    }

}
