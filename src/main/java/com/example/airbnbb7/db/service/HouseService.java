package com.example.airbnbb7.db.service;

import com.example.airbnbb7.dto.request.HouseRequest;
import com.example.airbnbb7.dto.response.HouseResponse;

public interface HouseService {

    HouseResponse saveHouse(HouseRequest houseRequest);

    HouseResponse deleteByIdHouse(Long houseId);

    HouseResponse updateHouse(Long id, HouseRequest houseRequest);

     void save(HouseRequest houseRequest);

}
