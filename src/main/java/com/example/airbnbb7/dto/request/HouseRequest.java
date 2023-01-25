package com.example.airbnbb7.dto.request;

import com.example.airbnbb7.db.enums.HouseType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HouseRequest {

    private String title;

    private String descriptionOfListing;

    private List<String> images;

    private Long maxOfGuests;

    private HouseType houseType;

    private LocationRequest location;

}
