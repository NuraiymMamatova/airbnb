package com.example.airbnbb7.db.service;

import com.example.airbnbb7.dto.response.BookingResponse;

import java.util.List;

public interface BookingService {

    List<BookingResponse> getBookingsByHouseId(Long houseId);

    Long getUserIdByBookingId(Long bookingId);

}
