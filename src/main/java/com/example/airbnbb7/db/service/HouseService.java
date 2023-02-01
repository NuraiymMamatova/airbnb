package com.example.airbnbb7.db.service;

import com.example.airbnbb7.db.enums.HouseType;
import com.example.airbnbb7.dto.response.AccommodationResponse;
import com.example.airbnbb7.dto.response.HouseResponse;
import com.example.airbnbb7.dto.response.HouseResponseSortedPagination;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HouseService {
    AccommodationResponse getLatestAccommodation ();
    List<HouseResponseSortedPagination> getAllPagination(HouseType houseType, String fieldToSort, String nameOfHouse, int page, int countOfHouses, String priceSort, String region);

    List<HouseResponse> getPopularHouses(Pageable pageable);

    List<HouseResponse> getAllPopularHouse(Pageable pageable);

    List<AccommodationResponse> getAllPopularApartments(Pageable pageable);

    double getRating (Long houseId);

    List<HouseResponseSortedPagination> sort (Pageable pageable, HouseType houseType, String region, String
            priceSort, String fieldToSort, List < HouseResponseSortedPagination > sortedHouseResponse);

}
