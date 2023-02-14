package com.example.airbnbb7.db.service;

import com.example.airbnbb7.db.customClass.SimpleResponse;
import com.example.airbnbb7.db.enums.HouseType;
import com.example.airbnbb7.dto.request.HouseRequest;
import com.example.airbnbb7.dto.response.AccommodationResponse;
import com.example.airbnbb7.dto.response.ApplicationResponse;
import com.example.airbnbb7.dto.response.HouseResponse;
import com.example.airbnbb7.dto.response.HouseResponseSortedPagination;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HouseService {

    SimpleResponse deleteByIdHouse(Long houseId);

    SimpleResponse save(HouseRequest houseRequest);

    SimpleResponse updateHouse(Long id, HouseRequest houseRequest);

    ApplicationResponse getAllPagination(HouseType houseType, String filter, String nameOfHouse, int page, int countOfHouses, String region,String popularAndLatest);
    List<AccommodationResponse> getLatestAccommodation(boolean popularHouse, boolean popularApartments);

    List<HouseResponseSortedPagination> getAllPagination(HouseType houseType, String fieldToSort, String nameOfHouse, int page, int countOfHouses, String priceSort, String region);

    AnnouncementService getAnnouncementById(Long houseId);

    List<HouseResponseSortedPagination> sort(Pageable pageable, HouseType houseType, String region, String
            priceSort, String fieldToSort, List<HouseResponseSortedPagination> sortedHouseResponse);

    List<HouseResponse> globalSearch(String searchEngine);
}
