package com.example.airbnbb7.converter.response;

import com.example.airbnbb7.db.customClass.Rating;
import com.example.airbnbb7.db.entities.House;
import com.example.airbnbb7.db.repository.FeedbackRepository;
import com.example.airbnbb7.db.repository.LocationRepository;
import com.example.airbnbb7.db.repository.UserRepository;
import com.example.airbnbb7.dto.response.HouseResponse;
import com.example.airbnbb7.dto.response.ProfileBookingHouseResponse;
import com.example.airbnbb7.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

@Component
public class HouseResponseConverter {
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;

    private final FeedbackRepository feedbackRepository;

    private final Rating rating;

    public HouseResponseConverter(LocationRepository locationRepository, UserRepository userRepository, FeedbackRepository feedbackRepository, Rating rating) {
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
        this.feedbackRepository = feedbackRepository;
        this.rating = rating;
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
        houseResponse.setRating(rating.getRating(feedbackRepository.getAllFeedbackByHouseId(house.getId())));
        return houseResponse;
    }
    public ProfileBookingHouseResponse view(House house) {
        if (house == null) {
            return null;
        }
        ProfileBookingHouseResponse houseResponse = new ProfileBookingHouseResponse();
        houseResponse.setId(house.getId());
        houseResponse.setPrice(house.getPrice());
        houseResponse.setTitle(house.getTitle());
        houseResponse.setDescriptionOfListing(house.getDescriptionOfListing());
        houseResponse.setImages(house.getImages());
        houseResponse.setMaxOfGuests(house.getMaxOfGuests());
        houseResponse.setHouseType(house.getHouseType());
        houseResponse.setLocation(locationRepository.findLocationByHouseId(house.getId()).orElseThrow(() -> new NotFoundException("House not found")));
        houseResponse.setOwner(userRepository.findUserById(house.getOwner().getId()));
        houseResponse.setRating(rating.getRating(feedbackRepository.getAllFeedbackByHouseId(house.getId())));
        return houseResponse;
    }

}