package com.example.airbnbb7.dto.response;

import com.example.airbnbb7.db.enums.HouseType;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HouseResponseSortedPagination {

    private Long id;

    private Double price;

    private String title;

    private String descriptionOfListing;

    private List<String> images;

    private Long maxOfGuests;

    private HouseType houseType;

    private LocationResponse locationResponse;

    private double houseRating;

    private Boolean isFavorite = false;

    public HouseResponseSortedPagination(Long id, Double price, String title, String descriptionOfListing, Long maxOfGuests, HouseType houseType, Boolean isFavorite) {
        this.id = id;
        this.price = price;
        this.title = title;
        this.descriptionOfListing = descriptionOfListing;
        this.maxOfGuests = maxOfGuests;
        this.houseType = houseType;
        this.isFavorite = isFavorite;
    }
}
