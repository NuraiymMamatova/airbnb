package com.example.airbnbb7.db.service.serviceImpl;

import com.example.airbnbb7.db.repository.BookingRepository;
import com.example.airbnbb7.db.service.BookingService;
import com.example.airbnbb7.dto.response.BookingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    @Override
    public List<BookingResponse> getBookingsByHouseId(Long houseId) {
        return bookingRepository.getBookingsByHouseId(houseId);
    }

    @Override
    public List<Long> getUserIdByBookingId(Long bookingId) {
        return bookingRepository.getUserIdByBookingId(bookingId);
    }
}
