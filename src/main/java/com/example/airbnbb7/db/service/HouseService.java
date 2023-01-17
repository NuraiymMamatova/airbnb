package com.example.airbnbb7.db.service;

import com.example.airbnbb7.db.entities.House;
import com.example.airbnbb7.dto.request.HouseRequest;
import com.example.airbnbb7.dto.response.HouseResponse;

public interface HouseService {

    HouseResponse saveHouse(HouseRequest houseRequest, Long id);

    HouseResponse acceptHouseById(Long id);
}
