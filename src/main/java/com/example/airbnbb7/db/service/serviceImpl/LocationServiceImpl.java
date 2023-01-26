package com.example.airbnbb7.db.service.serviceImpl;

import com.example.airbnbb7.db.repository.LocationRepository;
import com.example.airbnbb7.db.service.LocationService;
import com.example.airbnbb7.dto.response.LocationResponse;
import com.example.airbnbb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    @Override
    public LocationResponse findLocationByHouseId(Long houseId) {
        return locationRepository.findLocationByHouseId(houseId).orElseThrow(() -> new NotFoundException("Location not found!"));
    }
}
