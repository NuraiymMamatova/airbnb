package com.example.airbnbb7.db.service.serviceImpl;

import com.example.airbnbb7.db.entities.House;
import com.example.airbnbb7.db.entities.Location;
import com.example.airbnbb7.db.entities.User;
import com.example.airbnbb7.db.repository.LocationRepository;
import com.example.airbnbb7.db.repository.UserRepository;
import com.example.airbnbb7.db.service.LocationService;
import com.example.airbnbb7.db.service.UserService;
import com.example.airbnbb7.dto.request.LocationRequest;
import com.example.airbnbb7.dto.response.LocationResponse;
import com.example.airbnbb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final UserRepository userRepository;
    private final UserService userService;

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

    public void saveLocation(LocationRequest locationRequest) {
        House house = new House();
        User user  = userRepository.findByEmail(userService.getEmail()).orElseThrow(()-> new NotFoundException("Email not found"));
        locationRepository.saveLocation(locationRequest.getAddress(), locationRequest.getRegion(), locationRequest.getTownOrProvince(), user.getId().intValue());

    }
}
