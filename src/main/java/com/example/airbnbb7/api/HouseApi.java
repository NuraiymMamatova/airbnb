package com.example.airbnbb7.api;

import com.example.airbnbb7.db.enums.HousesStatus;
import com.example.airbnbb7.db.service.HouseService;
import com.example.airbnbb7.db.service.MarkerService;
import com.example.airbnbb7.dto.request.BookingRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/house")
@RequiredArgsConstructor
@Tag(name = "House API", description = "House API")
@CrossOrigin(origins = "*",maxAge = 3600)
public class HouseApi {

    private final HouseService houseService;

    @GetMapping("/announcements/{houseId}/{userId}")
    @Operation(summary = "House inner page", description = "Any user can go through to view the house")
    public MarkerService announcementsForVendor(@PathVariable Long houseId, @PathVariable Long userId,
                                                @RequestParam(required = false) Long bookingForUpdate,
                                                @RequestBody(required = false) BookingRequest bookingRequest,
                                                @RequestParam(required = false) HousesStatus houseStatus,
                                                @RequestParam(required = false) boolean addToFavorite,
                                                @RequestParam(required = false) boolean reject,
                                                @RequestParam(required = false) String message) {
        return houseService.getHouseForUserBooking(houseId, userId, bookingForUpdate, bookingRequest, houseStatus,addToFavorite, reject, message);
    }

}
