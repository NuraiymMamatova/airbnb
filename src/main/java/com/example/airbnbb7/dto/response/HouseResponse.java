package com.example.airbnbb7.dto.response;

import com.example.airbnbb7.db.enums.HouseType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;



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

    private LocationResponse location;

    private UserResponse owner;

    private Double rating;

    public HouseResponse(Long id, Double price, String title, String descriptionOfListing, Long maxOfGuests, HouseType houseType) {
        this.id = id;
        this.price = price;
        this.title = title;
        this.descriptionOfListing = descriptionOfListing;
        this.maxOfGuests = maxOfGuests;
        this.houseType = houseType;
    }

    public HouseResponse(Long id, Double price, String title, String descriptionOfListing, Long maxOfGuests, HouseType houseType, Double rating) {
        this.id = id;
        this.price = price;
        this.title = title;
        this.descriptionOfListing = descriptionOfListing;
        this.maxOfGuests = maxOfGuests;
        this.houseType = houseType;
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HouseResponse that = (HouseResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(price,
                that.price) && Objects.equals(title, that.title)
                && Objects.equals(descriptionOfListing, that.descriptionOfListing) &&
                Objects.equals(images, that.images) && Objects.equals(maxOfGuests,
                that.maxOfGuests) && houseType == that.houseType && Objects.equals(location,
                that.location) && Objects.equals(owner, that.owner);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
