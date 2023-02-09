package com.example.airbnbb7.db.service;

import com.example.airbnbb7.db.enums.HouseType;
import com.example.airbnbb7.dto.request.HouseRequest;
import com.example.airbnbb7.dto.response.ApplicationResponse;
import com.example.airbnbb7.dto.response.HouseResponse;
import com.example.airbnbb7.dto.response.HouseResponseSortedPagination;

import java.util.List;

public interface HouseService {

    HouseResponse deleteByIdHouse(Long houseId);

    HouseResponse save(HouseRequest houseRequest);

    HouseResponse updateHouse(Long id, HouseRequest houseRequest);

    ApplicationResponse getAllPagination(String search, HouseType houseType, String fieldToSort, int page, int countOfHouses, String priceSort, String region);

    AnnouncementService getAnnouncementById(Long houseId);

    List<HouseResponse> globalSearch(String searchEngine);
}
