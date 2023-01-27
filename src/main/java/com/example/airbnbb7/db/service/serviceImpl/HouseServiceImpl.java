package com.example.airbnbb7.db.service.serviceImpl;

import com.example.airbnbb7.db.repository.HouseRepository;
import com.example.airbnbb7.db.repository.UserRepository;
import com.example.airbnbb7.db.service.*;
import com.example.airbnbb7.dto.response.BookingResponse;
import com.example.airbnbb7.dto.response.HouseResponse;
import com.example.airbnbb7.dto.response.HouseResponseForVendor;
import com.example.airbnbb7.dto.response.UserResponse;
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

    @Override
    public HouseResponse getHouse(Long houseId, Long userId) {
        HouseResponse houseResponse = houseRepository.findHouseById(houseId).orElseThrow(() -> new NotFoundException("House not found!"));
        UserResponse user = userService.findUserById(userId);
        if (houseResponse.getOwner().equals(user)) {
            getHouseForVendor(houseId);
        } else {
            houseResponse.setImages(houseRepository.findImagesByHouseId(houseId));
            houseResponse.setOwner(userService.findUserById(houseRepository.findById(houseId).orElseThrow(() -> new NotFoundException("House not found!")).getOwner().getId()));
            houseResponse.setLocation(locationService.findLocationByHouseId(houseId));
        }
        return houseResponse;
    }

    @Override
    public HouseResponseForVendor getHouseForVendor(Long houseId) {
        HouseResponseForVendor houseResponseForVendor = houseRepository.findHouseByIdForVendor(houseId).orElseThrow(() -> new NotFoundException("House not found!"));
        houseResponseForVendor.setImages(houseRepository.findImagesByHouseId(houseId));
        List<BookingResponse> bookingResponses = new ArrayList<>();
        for (BookingResponse booking : bookingService.getBookingsByHouseId(houseId)) {
            List<Long> usersId = new ArrayList<>(bookingService.getUserIdByBookingId(booking.getId()));
            for (Long userId : usersId) {
                booking.setOwner(userService.findUserById(userId));
                bookingResponses.add(booking);
            }
        }
        houseResponseForVendor.setBookingResponses(bookingResponses);
        houseResponseForVendor.setFeedbacks(feedbackService.getFeedbacksByHouseId(houseId));
        houseResponseForVendor.setInFavorites(userService.inFavorite(houseId));
        houseResponseForVendor.setLocation(locationService.findLocationByHouseId(houseId));
        return houseResponseForVendor;
    }

}
