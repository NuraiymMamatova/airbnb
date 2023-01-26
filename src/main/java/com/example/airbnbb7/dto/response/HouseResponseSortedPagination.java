package com.example.airbnbb7.dto.response;

import com.example.airbnbb7.db.enums.HouseType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
@NoArgsConstructor
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

    private Boolean isFavorite = false;

    public HouseResponseSortedPagination(Long id, Double price, String title, String descriptionOfListing, Long maxOfGuests, HouseType houseType) {
        this.id = id;
        this.price = price;
        this.title = title;
        this.descriptionOfListing = descriptionOfListing;
        this.maxOfGuests = maxOfGuests;
        this.houseType = houseType;
    }
}
