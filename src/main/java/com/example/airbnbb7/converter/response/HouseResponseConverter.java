package com.example.airbnbb7.converter.response;

import com.example.airbnbb7.db.customClass.Rating;
import com.example.airbnbb7.db.entities.House;
import com.example.airbnbb7.db.repository.FeedbackRepository;
import com.example.airbnbb7.db.repository.HouseRepository;
import com.example.airbnbb7.db.repository.LocationRepository;
import com.example.airbnbb7.db.repository.UserRepository;
import com.example.airbnbb7.dto.response.ProfileHouseResponse;
import com.example.airbnbb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HouseResponseConverter {

    private final LocationRepository locationRepository;

    private final UserRepository userRepository;

    private final FeedbackRepository feedbackRepository;

    private final Rating rating;

    private final HouseRepository houseRepository;

    public ProfileHouseResponse view(House house) {
        if (house == null) {
            return null;
        }
        ProfileHouseResponse houseResponse = new ProfileHouseResponse();
        houseResponse.setId(house.getId());
        houseResponse.setPrice(house.getPrice());
        houseResponse.setTitle(house.getTitle());
        houseResponse.setDescriptionOfListing(house.getDescriptionOfListing());
        houseResponse.setImages(houseRepository.findImagesByHouseId(houseResponse.getId()));
        houseResponse.setMaxOfGuests(house.getMaxOfGuests());
        houseResponse.setHouseType(house.getHouseType());
        houseResponse.setLocation(locationRepository.findLocationByHouseId(house.getId()).orElseThrow(() -> new NotFoundException("House not found")));
        houseResponse.setOwner(userRepository.findUserById(house.getOwner().getId()));
        houseResponse.setRating(rating.getRating(feedbackRepository.getAllFeedbackByHouseId(house.getId())));
        return houseResponse;
    }

}