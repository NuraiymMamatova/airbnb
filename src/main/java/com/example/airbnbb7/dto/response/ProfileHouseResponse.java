package com.example.airbnbb7.dto.response;

import com.example.airbnbb7.db.enums.HouseType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileHouseResponse {

    private Long id;

    private Double price;

    private String title;

    private String descriptionOfListing;

    private List<String> images;

    private Long maxOfGuests;

    private HouseType houseType;

    private LocationResponse location;

    private UserResponse owner;

    private Double rating;

    private LocalDate checkIn;

    private LocalDate checkOut;

    private Long countOfFavorite;

    private Long countOfBooking;

    private Boolean isBlockCed;

    public ProfileHouseResponse(Long id, Double price, String title, String descriptionOfListing, Long maxOfGuests, HouseType houseType, Double rating, LocalDate checkIn, LocalDate checkOut) {
        this.id = id;
        this.price = price;
        this.title = title;
        this.descriptionOfListing = descriptionOfListing;
        this.maxOfGuests = maxOfGuests;
        this.houseType = houseType;
        this.rating = rating;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }
}
