package com.example.airbnbb7.db;

import com.example.airbnbb7.db.entities.House;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
@Setter
public class HouseResponseConverter {

    private List<HouseResponse> houseResponseList;

    public HouseResponse create(House house) {
        if (house == null) return null;
        HouseResponse houseResponse = new HouseResponse();
        houseResponse.setHouseType(house.getHouseType());
        houseResponse.setGuests(house.getGuests());
        houseResponse.setHousesBooked(house.getHousesBooked());
        houseResponse.setFeedbacks(house.getFeedbacks());
        houseResponse.setImages(house.getImages());
        houseResponse.setLocation(house.getLocation());
        houseResponse.setOwner(house.getOwner());
        houseResponse.setPrice(house.getPrice());
        houseResponse.setTitle(house.getTitle());
        houseResponse.setDescriptionOfListing(house.getDescriptionOfListing());
        return houseResponse;
    }

    public List<HouseResponse> getAll(List<House> houses) {
        List<HouseResponse> houseResponses = new ArrayList<>();
        for (House house : houses) {
            houseResponses.add(create(house));
        }
        return houseResponses;
    }
}
