package com.example.airbnbb7.db.service;

import com.example.airbnbb7.db.entities.Location;
import com.example.airbnbb7.dto.request.LocationRequest;
import com.example.airbnbb7.dto.response.LocationResponse;


public interface LocationService {

    LocationResponse updateLocation(Location location, LocationRequest locationRequest);

}
