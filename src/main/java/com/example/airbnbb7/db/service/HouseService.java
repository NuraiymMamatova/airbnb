package com.example.airbnbb7.db.service;

import com.example.airbnbb7.dto.response.HouseResponse;
import com.example.airbnbb7.dto.response.HouseResponseForVendor;

public interface HouseService {

    HouseResponse getHouse(Long houseId, Long userId);

    HouseResponseForVendor getHouseForVendor(Long houseId);

}
