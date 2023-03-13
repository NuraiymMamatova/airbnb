package com.example.airbnbb7.db.service.serviceImpl;

import com.example.airbnbb7.db.customClass.SimpleResponse;
import com.example.airbnbb7.db.entities.Booking;
import com.example.airbnbb7.db.entities.House;
import com.example.airbnbb7.db.entities.User;
import com.example.airbnbb7.db.repository.BookingRepository;
import com.example.airbnbb7.db.repository.HouseRepository;
import com.example.airbnbb7.db.repository.UserRepository;
import com.example.airbnbb7.db.service.BookingService;
import com.example.airbnbb7.dto.request.BookingRequest;
import com.example.airbnbb7.exceptions.BadCredentialsException;
import com.example.airbnbb7.exceptions.BadRequestException;
import com.example.airbnbb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final HouseRepository houseRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;


    @Override
    public SimpleResponse saveBooking(Long houseId, Authentication authentication, BookingRequest bookingRequest) {

        House house = houseRepository.findById(houseId).orElseThrow(() -> new NotFoundException("The house is not found!"));

        if (authentication != null) {
            User user = (User) authentication.getPrincipal();

            if (house.getOwner().getId() != user.getId()) {

                List<Booking> bookings = bookingRepository.getBookingsByUserId(user.getId());
                if (bookings != null) {
                    for (Booking b : bookings) {
                        if (b.getHouse().getId() == house.getId()) {
                            return new SimpleResponse("You already booked this house!");
                        }
                    }
                }
                Booking booking = new Booking();
                if (bookingRequest.getCheckIn() != null && bookingRequest.getCheckOut() != null) {
                    booking.setCheckIn(bookingRequest.getCheckIn());
                    booking.setCheckOut(bookingRequest.getCheckOut());
                } else {
                    throw new BadRequestException("The dates cannot be null!");
                }
                if (house.getMaxOfGuests() == house.getBookings()) {
                    throw new BadRequestException("The accommodation is full!");
                }

                for (Booking booking1 : house.getBookingDates()) {
                    if (booking1.getCheckOut() != null && booking1.getCheckOut().isBefore(LocalDate.now())) {
                        house.setBookings(house.getBookings() - 1);
                    }
                }
                booking.setPrice(house.getPrice());
                booking.setHouse(house);
                house.setBookings(house.getBookings() + 1);
                booking.setUsers(user);
                house.addBooking(booking);

                bookingRepository.save(booking);
            } else {
                throw new BadCredentialsException("You can't book this accommodation because you are the owner!");
            }
        } else {
            throw new BadRequestException("Authentication cannot be null!");
        }
        return new SimpleResponse("The accommodation is successfully booked!");
    }

    @Override
    public SimpleResponse updateBooking(Long bookingId, Authentication authentication, BookingRequest bookingRequest) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new NotFoundException("There is not such booking"));
        if (authentication != null) {
            User user = (User) authentication.getPrincipal();
            if (bookingRepository.getUserIdByBookingId(bookingId) == user.getId()) {
                if (bookingRequest.getCheckIn() != null)
                    booking.setCheckIn(bookingRequest.getCheckIn());
                if (bookingRequest.getCheckOut() != null)
                    booking.setCheckOut(bookingRequest.getCheckOut());
                bookingRepository.save(booking);

            } else {
                throw new BadCredentialsException("You can't update this booking, because it is not yours!");
            }
        } else {
            throw new BadRequestException("Authentication cannot be null!");
        }
        return new SimpleResponse("The booking of this accommodation is successfully updated!");
    }


}
