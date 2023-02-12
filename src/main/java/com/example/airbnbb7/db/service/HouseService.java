package com.example.airbnbb7.db.service;

import com.example.airbnbb7.db.customClass.SimpleResponse;
import com.example.airbnbb7.db.enums.HouseType;
import com.example.airbnbb7.dto.request.HouseRequest;
import com.example.airbnbb7.dto.response.ApplicationResponse;
import com.example.airbnbb7.dto.response.HouseResponse;

import java.util.List;

public interface HouseService {

    SimpleResponse deleteByIdHouse(Long houseId);

    SimpleResponse save(HouseRequest houseRequest);

    SimpleResponse updateHouse(Long id, HouseRequest houseRequest);

    ApplicationResponse getAllPagination(HouseType houseType, String filter, String nameOfHouse, int page, int countOfHouses, String region);

    AnnouncementService getAnnouncementById(Long houseId);

    List<HouseResponse> globalSearch(String searchEngine);
}
