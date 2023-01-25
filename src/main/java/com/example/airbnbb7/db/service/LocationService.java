package com.example.airbnbb7.db.service;

import com.example.airbnbb7.db.entities.Location;
import com.example.airbnbb7.dto.request.LocationRequest;
import com.example.airbnbb7.dto.response.LocationResponse;

public interface LocationService {

    Location updateLocation(Long locationId, LocationRequest locationRequest);

    LocationResponse findLocationByHouseId(Long houseId);
}
