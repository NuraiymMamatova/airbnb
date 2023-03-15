package com.example.airbnbb7.db.service;

import com.example.airbnbb7.db.customClass.SimpleResponse;
import com.example.airbnbb7.dto.request.HouseRequest;
import com.example.airbnbb7.dto.response.AccommodationResponse;
import com.example.airbnbb7.dto.response.ApplicationResponse;
import com.example.airbnbb7.dto.response.ApplicationResponseForAdmin;
import com.example.airbnbb7.dto.response.HouseResponseSortedPagination;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface HouseService {

    SimpleResponse deleteByIdHouse(Long houseId, Authentication authentication);

    SimpleResponse save(HouseRequest houseRequest, Authentication authentication) throws IOException;

    SimpleResponse updateHouse(Long id, Authentication authentication, HouseRequest houseRequest) throws IOException;

    ApplicationResponse getAllPagination(String search, String region, String popularOrTheLatest, String homeType, String price, Long page, Long pageSize, double userLatitude, double userLongitude) throws IOException;

    List<AccommodationResponse> getLatestAccommodation(boolean popularHouse, boolean popularApartments);

    MasterInterface getAnnouncementById(Long houseId, Authentication authentication);

    List<HouseResponseSortedPagination> searchNearby(double userLatitude, double userLongitude) throws IOException;

    SimpleResponse changeStatusOfHouse(Long houseId, String message, String housesStatus);

    ApplicationResponseForAdmin getAllStatusOfTheWholeHouseOnModeration(Long page, Long pageSize);

    List<HouseResponseSortedPagination> getAllHousing(String housesBooked, String houseType, String price, String popularOrTheLatest) throws IOException;

    SimpleResponse deleteImageById(String url, Long id, String houseOrFeedback);

}
