package com.example.airbnbb7.db.service.serviceImpl;

import com.example.airbnbb7.db.entities.House;
import com.example.airbnbb7.db.entities.Role;
import com.example.airbnbb7.db.entities.User;
import com.example.airbnbb7.db.enums.HousesStatus;
import com.example.airbnbb7.db.repository.BookingRepository;
import com.example.airbnbb7.db.repository.HouseRepository;
import com.example.airbnbb7.db.repository.UserRepository;
import com.example.airbnbb7.db.service.*;
import com.example.airbnbb7.dto.request.BookingRequest;
import com.example.airbnbb7.dto.response.BookingResponse;
import com.example.airbnbb7.dto.response.HouseResponse;
import com.example.airbnbb7.dto.response.HouseResponseForVendor;
import com.example.airbnbb7.exceptions.BadCredentialsException;
import com.example.airbnbb7.exceptions.BadRequestException;
import com.example.airbnbb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private final FavoriteHouseService favoriteHouseService;

    private final EmailService emailService;

    private final UserRepository userRepository;

    private final BookingRepository bookingRepository;

    @Override
    public MarkerService getHouseForUserBooking(Long houseId, Long userId,
                                                Long bookingIdForUpdate, BookingRequest bookingRequest,
                                                HousesStatus houseStatus, boolean addToFavorite,
                                                boolean reject, String message) {
        if (userId != null && houseId != null) {
            House house = houseRepository.findById(houseId).orElseThrow(() -> new NotFoundException("House not found"));
            User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found!"));
            Role role = new Role();
            for (Role position : user.getRoles()) {
                role = position;
            }
            if (role.getNameOfRole().equals("ADMIN")) {
                if (houseStatus != null && !houseStatus.equals(HousesStatus.REJECT)) {
                    return getHouseForAdmin(houseId, houseStatus);
                } else {
                    return getHouseForAdmin(houseId);
                }
            } else if (addToFavorite && !house.getOwner().equals(user) && role.getNameOfRole().equals("USER")) {
                favoriteHouseService.addHouseToFavorite(houseId, userId);
            } else if (reject && message != null) {
                if (role.getNameOfRole().equals("ADMIN")) {
                    rejectHouse(houseId, message);
                } else {
                    throw new BadCredentialsException("Request denied. You do not have permission to access this page.");
                }
            } else if (house.getOwner().equals(user)) {
                return getHouseForVendor(houseId);
            } else {
                HouseResponse houseResponse = houseRepository.findHouseById(houseId).orElseThrow(() -> new NotFoundException("House not found!"));
                houseResponse.setImages(houseRepository.findImagesByHouseId(houseId));
                if (bookingRequest != null) {
                    if (bookingIdForUpdate == null && house.getMaxOfGuests() != null && bookingRepository.getHouseIdByUserId(userId) != houseId) {
                        bookingService.saveBooking(houseId, userId, bookingRequest);
                        house.setMaxOfGuests(house.getMaxOfGuests() - 1);
                        if (house.getOwner().getCountOfBookedHouse() == null) {
                            house.getOwner().setCountOfBookedHouse(1L);
                        } else {
                            house.getOwner().setCountOfBookedHouse(house.getOwner().getCountOfBookedHouse() + 1);
                        }
                        houseRepository.save(house);
                    } else {
                        if (bookingIdForUpdate == null) {
                            bookingIdForUpdate = bookingRepository.getBookingIdByUserId(userId);
                            bookingService.updateBooking(bookingIdForUpdate, bookingRequest);
                        } else {
                            bookingService.updateBooking(bookingIdForUpdate, bookingRequest);
                        }
                    }
                }
                houseResponse.setOwner(userService.findUserById(houseRepository.findById(houseId).orElseThrow(() -> new NotFoundException("House not found!")).getOwner().getId()));
                houseResponse.setFeedbacks(feedbackService.getFeedbacksByHouseId(houseId));
                houseResponse.setLocation(locationService.findLocationByHouseId(houseId));
                return houseResponse;
            }
        }
        throw new BadRequestException("House id or user id cannot be null!");
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

    @Override
    public HouseResponse getHouseForAdmin(Long houseId, HousesStatus houseStatus) {
        HouseResponse houseResponse = getHouseForAdmin(houseId);
        House house = houseRepository.findById(houseId).orElseThrow(() -> new NotFoundException("House not found!"));
        house.setHousesStatus(houseStatus);
        houseRepository.save(house);
        return houseResponse;
    }

    @Override
    public HouseResponse getHouseForAdmin(Long houseId) {
        HouseResponse houseResponse = houseRepository.findHouseById(houseId).orElseThrow(() -> new NotFoundException("House not found!"));
        houseResponse.setImages(houseRepository.findImagesByHouseId(houseId));
        houseResponse.setOwner(userService.findUserById(houseRepository.findById(houseId).orElseThrow(() -> new NotFoundException("House not found!")).getOwner().getId()));
        houseResponse.setLocation(locationService.findLocationByHouseId(houseId));
        return houseResponse;
    }

    @Override
    public ResponseEntity<String> rejectHouse(Long houseId, String message) {
        House house = houseRepository.findById(houseId).orElseThrow(() -> new NotFoundException("House not found!"));
        house.setHousesStatus(HousesStatus.REJECT);
        houseRepository.save(house);
        emailService.sendMessage(house.getOwner().getEmail(), "House rejected :(", message);
        return ResponseEntity.status(HttpStatus.OK).body("Successfully sent :)");
    }

}
