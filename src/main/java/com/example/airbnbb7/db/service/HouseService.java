package com.example.airbnbb7.db.service;

import com.example.airbnbb7.db.entities.User;
import com.example.airbnbb7.db.customClass.SimpleResponse;
import com.example.airbnbb7.db.enums.HouseType;
import com.example.airbnbb7.dto.request.HouseRequest;
import com.example.airbnbb7.dto.response.ApplicationResponse;
import com.example.airbnbb7.dto.response.HouseResponse;
import com.example.airbnbb7.dto.response.HouseResponseSortedPagination;

import java.util.List;

public interface HouseService {

    SimpleResponse deleteByIdHouse(Long houseId, Long userId);

    SimpleResponse save(HouseRequest houseRequest, User user);

    SimpleResponse updateHouse(Long id, Long userId, HouseRequest houseRequest);

    ApplicationResponse getAllPagination(HouseType houseType, String filter, String nameOfHouse, int page, int countOfHouses, String region,String popularAndLatest);

    AnnouncementService getAnnouncementById(Long houseId, Long userId);

    List<HouseResponse> globalSearch(String searchEngine);
}
