package com.example.airbnbb7.dto.response;

import com.example.airbnbb7.db.enums.HouseType;
import com.example.airbnbb7.db.service.AnnouncementService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
@NoArgsConstructor
public class AnnouncementResponseForUser implements AnnouncementService {

    private Long id;

    private String title;

    private String descriptionOfListing;

    private List<String> images;

    private Long maxOfGuests;

    private HouseType houseType;

    private LocationResponse location;

    private UserResponse owner;

    private BookingResponse bookingResponse;

    private List<FeedbackResponse> feedbacks;

    public AnnouncementResponseForUser(Long id, String title, String descriptionOfListing, Long maxOfGuests, HouseType houseType) {
        this.id = id;
        this.title = title;
        this.descriptionOfListing = descriptionOfListing;
        this.maxOfGuests = maxOfGuests;
        this.houseType = houseType;
    }

}
