package com.example.airbnbb7.db;

import com.example.airbnbb7.db.entities.Location;
import com.example.airbnbb7.db.enums.HouseType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HouseRequest {
    private Double price;
    private String title;
    private String descriptionOfListing;
    private List<String> images;
    private Long maxOfGuests;
    private HouseType houseType;
    private Location location;
}
