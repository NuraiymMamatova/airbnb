package com.example.airbnbb7.dto.response;

import com.example.airbnbb7.db.customClass.Rating;
import com.example.airbnbb7.db.enums.HouseType;
import com.example.airbnbb7.db.service.MasterInterface;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
@NoArgsConstructor
public class AnnouncementResponseForUser implements MasterInterface {

    private Long id;

    private String title;

    private Double price;

    private String descriptionOfListing;

    private List<String> images;

    private Long maxOfGuests;

    private Rating rating;

    private HouseType houseType;

    private LocationResponse location;

    private UserResponse owner;

    private BookingResponse bookingResponse;

    private List<FeedbackResponse> feedbacks;

    public AnnouncementResponseForUser(Long id, String title, Double price, String descriptionOfListing, Long maxOfGuests, HouseType houseType) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.descriptionOfListing = descriptionOfListing;
        this.maxOfGuests = maxOfGuests;
        this.houseType = houseType;
    }

}
