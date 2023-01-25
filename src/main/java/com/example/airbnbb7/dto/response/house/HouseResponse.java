package com.example.airbnbb7.dto.response.house;

import com.example.airbnbb7.db.enums.HouseType;
import com.example.airbnbb7.db.service.MarkerService;
import com.example.airbnbb7.dto.response.FeedbackResponse;
import com.example.airbnbb7.dto.response.LocationResponse;
import com.example.airbnbb7.dto.response.user.UserResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class HouseResponse implements MarkerService {

    private Long id;

    private String title;

    private String descriptionOfListing;

    private List<String> images;

    private Long maxOfGuests;

    private HouseType houseType;

    private LocationResponse location;

    private UserResponse owner;

    private List<FeedbackResponse> feedbacks;

    public HouseResponse(Long id, String title, String descriptionOfListing, Long maxOfGuests, HouseType houseType) {
        this.id = id;
        this.title = title;
        this.descriptionOfListing = descriptionOfListing;
        this.maxOfGuests = maxOfGuests;
        this.houseType = houseType;
    }

}
