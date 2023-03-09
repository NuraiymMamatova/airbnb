package com.example.airbnbb7.api;

import com.example.airbnbb7.db.customClass.SimpleResponse;
import com.example.airbnbb7.db.service.BookingService;
import com.example.airbnbb7.dto.request.BookingRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/bookings")
@Tag(name = "Booking Api", description = "Booking Api")
public class BookingApi {

    private final BookingService bookingService;

    @PostMapping("/houses/{houseId}")
    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Save booking", description = "Save bookings in database")
    public SimpleResponse saveBooking(@PathVariable Long houseId,
                                      Authentication authentication,
                                      @RequestBody BookingRequest bookingRequest) {
        return bookingService.saveBooking(houseId, authentication, bookingRequest);
    }

    @PutMapping("/{bookingId}")
    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Update the booking", description = "Update the bookings in database")
    public SimpleResponse updateBooking(@PathVariable Long bookingId,
                                        Authentication authentication,
                                        @RequestBody BookingRequest bookingRequest) {
        return bookingService.updateBooking(bookingId, authentication, bookingRequest);
    }
}
