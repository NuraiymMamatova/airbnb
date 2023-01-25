package com.example.airbnbb7.db.service.serviceImpl;

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
    public Location updateLocation(Long locationId, LocationRequest locationRequest) {
        Location location = locationRepository.findById(locationId).orElseThrow(() -> new NotFoundException("Location not found!"));
        if (locationRequest.getAddress() != null) {
            location.setAddress(locationRequest.getAddress());
        }
        if (locationRequest.getRegion() != null) {
            location.setRegion(locationRequest.getRegion());
        }
        if (locationRequest.getTownOrProvince() != null) {
            location.setTownOrProvince(locationRequest.getTownOrProvince());
        }
        locationRepository.save(location);
        return location;
    }

    @Override
    public LocationResponse findLocationByHouseId(Long houseId) {
        return locationRepository.findLocationByHouseId(houseId).orElseThrow(() -> new NotFoundException("Location not found!"));
    }
}
