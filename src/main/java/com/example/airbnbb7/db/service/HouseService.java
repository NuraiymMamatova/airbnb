package com.example.airbnbb7.db.service;

import com.example.airbnbb7.dto.response.HouseResponseForVendor;

public interface HouseService {

    MarkerService getHouse(Long houseId, Long userId);

    HouseResponseForVendor getHouseForVendor(Long houseId);

}
