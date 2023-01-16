package com.example.airbnbb7.db;

import com.example.airbnbb7.db.entities.House;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HouseRequestConverter {

    public House save(HouseRequest houseRequest){
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

    public void update(House house,HouseRequest houseRequest){
        house.setHouseType(houseRequest.getHouseType());
        house.setDescriptionOfListing(houseRequest.getDescriptionOfListing());
        house.setPrice(houseRequest.getPrice());
        house.setLocation(houseRequest.getLocation());
        house.setTitle(houseRequest.getTitle());
        house.setImages(houseRequest.getImages());
        house.setMaxOfGuests(houseRequest.getMaxOfGuests());
    }
}
