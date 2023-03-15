package com.example.airbnbb7.dto.response;

import com.example.airbnbb7.db.customClass.Rating;
import com.example.airbnbb7.db.enums.HouseType;
import com.example.airbnbb7.db.service.MasterInterface;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Component
@NoArgsConstructor
public class AnnouncementResponseForVendor implements MasterInterface {

    private Long id;

    private String title;

    private String descriptionOfListing;

    private Map<Long, String> images;

    private Long maxOfGuests;

    private Rating rating;

    private HouseType houseType;

    private LocationResponse location;

    private List<UserResponseForVendor> inFavorites;

    private List<BookingResponse> bookingResponses;

    private List<FeedbackResponse> feedbacks;

    public AnnouncementResponseForVendor(Long id, String title, String descriptionOfListing, Long maxOfGuests, HouseType houseType) {
        this.id = id;
        this.title = title;
        this.descriptionOfListing = descriptionOfListing;
        this.maxOfGuests = maxOfGuests;
        this.houseType = houseType;
    }
}
