package com.example.airbnbb7.db.service;

import com.example.airbnbb7.db.customClass.SimpleResponse;
import com.example.airbnbb7.dto.request.BookingRequest;
import org.springframework.security.core.Authentication;

public interface BookingService {

    SimpleResponse saveBooking(Long houseId, Authentication authentication, BookingRequest bookingRequest);

    SimpleResponse updateBooking(Long bookingId, Authentication authentication, BookingRequest bookingRequest);
}
