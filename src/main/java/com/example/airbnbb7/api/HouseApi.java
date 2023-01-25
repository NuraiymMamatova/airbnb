package com.example.airbnbb7.api;

import com.example.airbnbb7.db.enums.HousesStatus;
import com.example.airbnbb7.db.service.HouseService;
import com.example.airbnbb7.db.service.MarkerService;
import com.example.airbnbb7.dto.request.BookingRequest;
import com.example.airbnbb7.dto.request.HouseRequest;
import com.example.airbnbb7.dto.response.house.HouseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/house")
@RequiredArgsConstructor
public class HouseApi {

    private final HouseService houseService;

    @GetMapping("/announcements/{houseId}/{userId}")
    public MarkerService announcementsForVendor(@PathVariable Long houseId, @PathVariable Long userId,
                                                @RequestParam(required = false) Long bookingForUpdate,
                                                @RequestBody(required = false) BookingRequest bookingRequest,
                                                @RequestParam(required = false) HousesStatus houseStatus,
                                                @RequestParam(required = false) boolean delete,
                                                @RequestParam(required = false) boolean addToFavorite,
                                                @RequestParam(required = false) boolean reject,
                                                @RequestParam(required = false) String message) {
        return houseService.getHouseForUserBooking(houseId, userId, bookingForUpdate, bookingRequest, houseStatus, delete, addToFavorite, reject, message);
    }

    @PostMapping("/update/{houseId}")
    public HouseResponse updateHouse(@PathVariable Long houseId, @RequestBody HouseRequest houseRequest) {
        return houseService.updateHouse(houseId, houseRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return houseService.deleteHouse(id);
    }

}
