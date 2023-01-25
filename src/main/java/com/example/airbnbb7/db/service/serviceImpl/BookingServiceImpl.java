package com.example.airbnbb7.db.service.serviceImpl;

import com.example.airbnbb7.db.entities.Booking;
import com.example.airbnbb7.db.entities.House;
import com.example.airbnbb7.db.entities.User;
import com.example.airbnbb7.db.repository.BookingRepository;
import com.example.airbnbb7.db.repository.HouseRepository;
import com.example.airbnbb7.db.repository.UserRepository;
import com.example.airbnbb7.db.service.BookingService;
import com.example.airbnbb7.dto.request.BookingRequest;
import com.example.airbnbb7.dto.response.BookingResponse;
import com.example.airbnbb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    private final UserRepository userRepository;
    
    private final HouseRepository houseRepository;

    @Override
    public BookingResponse saveBooking(Long houseId, Long userId, BookingRequest bookingRequest) {
        Booking booking = new Booking();
        booking.setPrice(bookingRequest.getPrice());
        booking.setCheckIn(bookingRequest.getCheckIn());
        booking.setCheckOut(bookingRequest.getCheckOut());
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found!"));
        House house = houseRepository.findById(houseId).orElseThrow(() -> new NotFoundException("House not found!"));
        house.addBooking(booking);
        booking.setHouse(house);
        booking.addUser(user);
        user.addBooking(booking);
        bookingRepository.save(booking);
        return bookingRepository.findBookingById(booking.getId()).orElseThrow(() -> new NotFoundException("Booking not found!"));
    }

    @Override
    public BookingResponse updateBooking(Long bookingId, BookingRequest bookingRequest) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new NotFoundException("Booking not found!"));
        if (bookingRequest.getPrice() != null) {
            booking.setPrice(bookingRequest.getPrice());
        }
        if (bookingRequest.getCheckIn() != null) {
            booking.setCheckIn(bookingRequest.getCheckIn());
        }
        if (bookingRequest.getCheckOut() != null) {
            booking.setCheckOut(bookingRequest.getCheckOut());
        }
        bookingRepository.save(booking);
        return bookingRepository.findBookingById(booking.getId()).orElseThrow(() -> new NotFoundException("Booking not found!"));
    }

    @Override
    public List<BookingResponse> getBookingsByHouseId(Long houseId) {
        return bookingRepository.getBookingsByHouseId(houseId);
    }

    @Override
    public List<Long> getUserIdByBookingId(Long bookingId) {
        return bookingRepository.getUserIdByBookingId(bookingId);
    }
}
