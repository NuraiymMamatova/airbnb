package com.example.airbnbb7.db.service;

import com.example.airbnbb7.dto.response.LocationResponse;

public interface LocationService {

    LocationResponse findLocationByHouseId(Long houseId);
}
