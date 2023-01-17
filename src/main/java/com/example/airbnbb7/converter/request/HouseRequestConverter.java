package com.example.airbnbb7.converter.request;

import com.example.airbnbb7.db.entities.House;
import com.example.airbnbb7.dto.request.HouseRequest;
import org.springframework.stereotype.Component;

@Component
public class HouseRequestConverter {

    public House save(HouseRequest houseRequest) {
        if (houseRequest == null) {
            return null;
        }
        House house = new House();
        house.setHouseType(houseRequest.getHouseType());
        house.setDescriptionOfListing(houseRequest.getDescriptionOfListing());
        house.setPrice(houseRequest.getPrice());
        house.setLocation(houseRequest.getLocation());
        house.setTitle(houseRequest.getTitle());
        house.setImages(houseRequest.getImages());
        house.setMaxOfGuests(houseRequest.getMaxOfGuests());
        return house;
    }

    public void update(House house, HouseRequest houseRequest) {
        house.setHouseType(houseRequest.getHouseType());
        house.setDescriptionOfListing(houseRequest.getDescriptionOfListing());
        house.setPrice(houseRequest.getPrice());
        house.setLocation(houseRequest.getLocation());
        house.setTitle(houseRequest.getTitle());
        house.setImages(houseRequest.getImages());
        house.setMaxOfGuests(houseRequest.getMaxOfGuests());
    }
}
