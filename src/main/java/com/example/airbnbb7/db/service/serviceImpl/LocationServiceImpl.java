package com.example.airbnbb7.db.service.serviceImpl;

import com.example.airbnbb7.db.entities.House;
import com.example.airbnbb7.db.entities.Location;
import com.example.airbnbb7.db.repository.LocationRepository;
import com.example.airbnbb7.db.service.LocationService;
import com.example.airbnbb7.dto.request.LocationRequest;
import com.example.airbnbb7.dto.response.LocationResponse;
import com.example.airbnbb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    @Override
    public LocationResponse saveLocation(House house, LocationRequest locationRequest) {
        Location location = new Location();
        location.setAddress(locationRequest.getAddress());
        location.setRegion(locationRequest.getRegion());
        location.setTownOrProvince(locationRequest.getTownOrProvince());
        location.setHouse(house);
        locationRepository.save(location);
        return locationRepository.findLocationByHouseId(house.getId()).orElseThrow(() -> new NotFoundException("not found location id"));
    }

    @Override
    public LocationResponse updateLocation(Location location, LocationRequest locationRequest) {
        location.setAddress(locationRequest.getAddress());
        location.setRegion(locationRequest.getRegion());
        location.setTownOrProvince(locationRequest.getTownOrProvince());
        locationRepository.save(location);
        return locationRepository.findLocationById(location.getId()).orElseThrow(() -> new NotFoundException("not found location id"));
    }
}
