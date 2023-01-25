package com.example.airbnbb7.db.service;

import com.example.airbnbb7.dto.request.BookingRequest;
import com.example.airbnbb7.dto.response.BookingResponse;

import java.util.List;

public interface BookingService {

    BookingResponse saveBooking(Long houseId, Long userId, BookingRequest bookingRequest);

    BookingResponse updateBooking(Long bookingId, BookingRequest bookingRequest);

    List<BookingResponse> getBookingsByHouseId(Long houseId);

    List<Long> getUserIdByBookingId(Long bookingId);
}
