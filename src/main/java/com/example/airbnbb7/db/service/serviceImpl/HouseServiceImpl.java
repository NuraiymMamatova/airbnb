package com.example.airbnbb7.db.service.serviceImpl;

import com.example.airbnbb7.db.entities.House;
import com.example.airbnbb7.db.repository.HouseRepository;
import com.example.airbnbb7.db.service.HouseService;
import com.example.airbnbb7.dto.response.HouseResponse;
import com.example.airbnbb7.dto.response.LocationResponse;
import com.example.airbnbb7.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService {

    private final HouseRepository houseRepository;

    @Override
    public List<HouseResponse> globalSearch(String searchEngine) {
        String[] searchEngines = searchEngine.toUpperCase().split(" ");
        List<HouseResponse> globalHouses = new ArrayList<>();
        List<House> houses = new ArrayList<>();
        for (House house : houseRepository.findAll()) {
            for (String search : searchEngines) {
                if (house.getTitle().toUpperCase().contains(search) || house.getLocation().getRegion().toUpperCase().contains(search) ||
                        house.getLocation().getAddress().toUpperCase().contains(search) || house.getHouseType().toString().toUpperCase().contains(search)) {
                    if (!houses.contains(house)) {
                        houses.add(house);
                    }
                }
            }
        }

        for (House house : houses) {
            HouseResponse houseResponse = new HouseResponse(house.getId(), house.getPrice(), house.getTitle(),
                    house.getDescriptionOfListing(), house.getMaxOfGuests(), house.getHouseType());
            houseResponse.setOwner(new UserResponse(house.getOwner().getId(), house.getOwner().getName(),
                    house.getOwner().getEmail(), house.getOwner().getImage()));
            houseResponse.setLocation(new LocationResponse(house.getLocation().getId(), house.getLocation().getTownOrProvince(),
                    house.getLocation().getAddress(), house.getLocation().getRegion()));
            houseResponse.setImages(house.getImages());
            globalHouses.add(houseResponse);
        }
        return globalHouses;
    }
}
