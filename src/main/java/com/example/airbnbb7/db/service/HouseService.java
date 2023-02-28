package com.example.airbnbb7.db.service;

import com.example.airbnbb7.db.customClass.SimpleResponse;
import com.example.airbnbb7.db.enums.HousesBooked;
import com.example.airbnbb7.db.enums.HousesStatus;
import com.example.airbnbb7.dto.request.HouseRequest;
import com.example.airbnbb7.dto.response.AccommodationResponse;
import com.example.airbnbb7.dto.response.ApplicationResponse;
import com.example.airbnbb7.dto.response.ApplicationResponseForAdmin;
import com.example.airbnbb7.dto.response.HouseResponseSortedPagination;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HouseService {

    SimpleResponse deleteByIdHouse(Long houseId, Authentication authentication);

    SimpleResponse save(HouseRequest houseRequest, Authentication authentication);

    SimpleResponse updateHouse(Long id, Authentication authentication, HouseRequest houseRequest);

    ApplicationResponse getAllPagination(String search, String region, String popularOrTheLatest, String homeType, String price, Long page, Long pageSize, double userLatitude, double userLongitude);

    List<AccommodationResponse> getLatestAccommodation(boolean popularHouse, boolean popularApartments);

    AnnouncementService getAnnouncementById(Long houseId, Authentication authentication);

    List<HouseResponseSortedPagination> searchNearby(double userLatitude, double userLongitude);

    SimpleResponse changeStatusOfHouse(Long houseId, String message, HousesStatus housesStatus);

    ApplicationResponseForAdmin getAllStatusOfTheWholeHouseOnModeration(Long page, Long pageSize);

    List<HouseResponseSortedPagination> getAllHousing(HousesBooked housesBooked, String houseType, String price, String popularOrTheLatest);

}
