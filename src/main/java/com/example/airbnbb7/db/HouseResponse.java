package com.example.airbnbb7.db;

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
    private Double price;
    private String title;
    private String descriptionOfListing;
    private List<String> images;
    private Long maxOfGuests;
    private HouseType houseType;
    private HousesBooked housesBooked;
    private Location location;
    private List<Booking> bookingDates;
    private List<User> guests;
    private List<Feedback> feedbacks;
    private User owner;
}
