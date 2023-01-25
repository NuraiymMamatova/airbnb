package com.example.airbnbb7.db.service;

import com.example.airbnbb7.db.enums.HousesStatus;
import com.example.airbnbb7.dto.request.BookingRequest;
import com.example.airbnbb7.dto.request.HouseRequest;
import com.example.airbnbb7.dto.response.house.HouseResponse;
import com.example.airbnbb7.dto.response.house.HouseResponseForVendor;
import org.springframework.http.ResponseEntity;

public interface HouseService {

    MarkerService getHouseForUserBooking(Long houseId, Long userIdForBooking,
                                         Long bookingIdForUpdate, BookingRequest booking,
                                         HousesStatus houseStatus, boolean addToFavorite,
                                         boolean reject, String message);

    HouseResponseForVendor getHouseForVendor(Long houseId);

    HouseResponse getHouseForAdmin(Long houseId, HousesStatus houseStatus);

    ResponseEntity<String> rejectHouse(Long houseId, String message);

    HouseResponse getHouseForAdmin(Long houseId);

}
