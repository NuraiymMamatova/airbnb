package com.example.airbnbb7.converter.response;

import com.example.airbnbb7.db.entities.House;
import com.example.airbnbb7.dto.response.HouseResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HouseResponseConverter {


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
        houseResponse.setLocation(house.getLocation());
        houseResponse.setOwner(house.getOwner());
        return houseResponse;
    }

    public List<HouseResponse> viewAllHouse(List<House> houses) {
        List<HouseResponse> houseResponses = new ArrayList<>();
        for (House house : houses) {
            houseResponses.add(viewHouse(house));
        }
        return houseResponses;
    }
}