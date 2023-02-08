package com.example.airbnbb7.converter.response;

import com.example.airbnbb7.db.entities.House;
import com.example.airbnbb7.db.repository.LocationRepository;
import com.example.airbnbb7.db.repository.UserRepository;
import com.example.airbnbb7.dto.response.HouseResponse;
import com.example.airbnbb7.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

@Component
public class HouseResponseConverter {
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;

    public HouseResponseConverter(LocationRepository locationRepository,
                                  UserRepository userRepository) {
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
    }

    public HouseResponse viewHouse(House house) {
        if (house == null) {
            return null;
        }
        HouseResponse houseResponse = new HouseResponse();
        houseResponse.setId(house.getId());
        houseResponse.setPrice(house.getPrice());
        houseResponse.setTitle(house.getTitle());
        houseResponse.setDescriptionOfListing(house.getDescriptionOfListing());
        houseResponse.setImages(house.getImages());
        houseResponse.setMaxOfGuests(house.getMaxOfGuests());
        houseResponse.setHouseType(house.getHouseType());
        houseResponse.setLocation(locationRepository.findLocationByHouseId(house.getId()).orElseThrow(() -> new NotFoundException("House not found")));
        houseResponse.setOwner(userRepository.findUserById(house.getOwner().getId()));

        return houseResponse;
    }

}