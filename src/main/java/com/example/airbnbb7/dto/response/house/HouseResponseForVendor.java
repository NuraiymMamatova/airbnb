package com.example.airbnbb7.dto.response.house;

import com.example.airbnbb7.db.enums.HouseType;
import com.example.airbnbb7.db.service.MarkerService;
import com.example.airbnbb7.dto.response.BookingResponse;
import com.example.airbnbb7.dto.response.FeedbackResponse;
import com.example.airbnbb7.dto.response.LocationResponse;
import com.example.airbnbb7.dto.response.user.UserResponseForVendor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class HouseResponseForVendor implements MarkerService {

    private Long id;

    private String title;

    private String descriptionOfListing;

    private List<String> images;

    private Long maxOfGuests;

    private List<UserResponseForVendor> inFavorites;

    private HouseType houseType;

    private LocationResponse location;

    private List<BookingResponse> bookingResponses;

    private List<FeedbackResponse> feedbacks;

    public HouseResponseForVendor(Long id, String title, String descriptionOfListing, Long maxOfGuests, HouseType houseType) {
        this.id = id;
        this.title = title;
        this.descriptionOfListing = descriptionOfListing;
        this.maxOfGuests = maxOfGuests;
        this.houseType = houseType;
    }
}
