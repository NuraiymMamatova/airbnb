package com.example.airbnbb7.dto.response;

import com.example.airbnbb7.db.entities.Booking;
import com.example.airbnbb7.db.entities.Feedback;
import com.example.airbnbb7.db.entities.Location;
import com.example.airbnbb7.db.entities.User;
import com.example.airbnbb7.db.enums.HouseType;
import com.example.airbnbb7.db.enums.HousesBooked;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HouseResponse {

    private Long id;
    private Double price;
    private String title;
    private String descriptionOfListing;
    private List<String> images;
    private Long maxOfGuests;
    private HouseType houseType;
    private Location location;
    private User owner;
}