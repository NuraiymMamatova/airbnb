package com.example.airbnbb7.converter.request;

import com.example.airbnbb7.db.entities.House;
import com.example.airbnbb7.db.entities.Location;
import com.example.airbnbb7.db.repository.LocationRepository;
import com.example.airbnbb7.dto.request.HouseRequest;
import org.springframework.stereotype.Component;

@Component
public class HouseRequestConverter {
    private final LocationRepository locationRepository;

    public HouseRequestConverter(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public House saveHouse(HouseRequest houseRequest) {
        if (houseRequest == null) {
            return null;
        }

        House house = new House();
        house.setPrice(houseRequest.getPrice());
        house.setTitle(houseRequest.getTitle());
        house.setDescriptionOfListing(houseRequest.getDescriptionOfListing());
        house.setImages(houseRequest.getImages());
        house.setMaxOfGuests(houseRequest.getMaxOfGuests());
        house.setHouseType(houseRequest.getHouseType());
        house.setLocation(house.getLocation());
        System.out.println(house.getLocation());
        return house;
    }

    public void update(House house, HouseRequest houseRequest) {
        if (houseRequest.getPrice() != null) {
            house.setPrice(houseRequest.getPrice());
        }
        if (houseRequest.getTitle() != null) {
            house.setTitle(houseRequest.getTitle());
        }
        if (houseRequest.getDescriptionOfListing() != null) {
            house.setDescriptionOfListing(houseRequest.getDescriptionOfListing());
        }
        if (houseRequest.getImages() != null) {
            house.setImages(houseRequest.getImages());
        }
        if (houseRequest.getMaxOfGuests() != null) {
            house.setMaxOfGuests(houseRequest.getMaxOfGuests());
        }
        if (houseRequest.getHouseType() != null) {
            house.setHouseType(houseRequest.getHouseType());
        }
        if (houseRequest.getLocation() != null) {
            Location location = new Location();

            if (houseRequest.getLocation().getAddress() != null) {
                location.setAddress(houseRequest.getLocation().getAddress());
            }
            if (houseRequest.getLocation().getRegion() != null) {
                location.setRegion(houseRequest.getLocation().getRegion());
            }
            if (houseRequest.getLocation().getTownOrProvince() != null) {
                location.setTownOrProvince(houseRequest.getLocation().getTownOrProvince());
            }
            house.setLocation(location);
        }

    }

}

