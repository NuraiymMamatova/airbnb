package com.example.airbnbb7.db.service.serviceImpl;

import com.example.airbnbb7.converter.request.HouseRequestConverter;
import com.example.airbnbb7.converter.response.HouseResponseConverter;
import com.example.airbnbb7.db.entities.House;
import com.example.airbnbb7.db.entities.Location;
import com.example.airbnbb7.db.entities.User;
import com.example.airbnbb7.db.enums.HousesStatus;
import com.example.airbnbb7.db.repository.HouseRepository;
import com.example.airbnbb7.db.repository.LocationRepository;
import com.example.airbnbb7.db.repository.UserRepository;
import com.example.airbnbb7.db.service.HouseService;
import com.example.airbnbb7.db.service.LocationService;
import com.example.airbnbb7.db.service.UserService;
import com.example.airbnbb7.dto.request.HouseRequest;
import com.example.airbnbb7.dto.response.HouseResponse;
import com.example.airbnbb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService {

    private final HouseRepository houseRepository;

    private final HouseRequestConverter houseRequestConverter;

    private final HouseResponseConverter houseResponseConverter;

    private final UserRepository userRepository;

    private final LocationService locationService;

    private final UserService userService;

    private final LocationRepository locationRepository;


    @Override
    public HouseResponse saveHouse(HouseRequest houseRequest) {
        House house = houseRequestConverter.saveHouse(houseRequest);
        house.setDateHouseCreated(LocalDate.now());
        User user = userRepository.findByEmail(userService.getEmail()).orElseThrow(() -> new NotFoundException("Email not found"));
        user.addHouse(house);
        house.setOwner(user);
        house.setHousesStatus(HousesStatus.ON_MODERATION);
        houseRepository.save(house);
        locationService.saveLocation(house, houseRequest.getLocation());
        return houseResponseConverter.viewHouse(house);
    }

    @Override
    public HouseResponse deleteByIdHouse(Long houseId) {
        House house = houseRepository.findById(houseId).get();
        houseRepository.delete(house);
        return houseResponseConverter.viewHouse(house);
    }

    @Override
    public HouseResponse updateHouse(Long id, HouseRequest houseRequest) {
        House house = houseRepository.findById(id).get();
        houseRequestConverter.update(house, houseRequest);
        return houseResponseConverter.viewHouse(houseRepository.save(house));
    }

    public void save(HouseRequest houseRequest) {

        User user = userRepository.findByEmail(userService.getEmail()).orElseThrow(() -> new NotFoundException("Email not found"));
        houseRepository.saveHouse(houseRequest.getPrice(), houseRequest.getTitle(), houseRequest.getMaxOfGuests(), LocalDate.now(), HousesStatus.ON_MODERATION.ordinal(), user.getId().intValue());

        locationService.saveLocation(houseRequest.getLocation());
        Location location = locationRepository.findById(locationRepository.locationMaxId()).get();
        House house = houseRepository.findById(houseRepository.houseMaxId()).get();
        location.setHouse(house);
        house.setLocation(location);
        house.setOwner(user);
        houseRepository.save(house);

    }

}
