package com.example.airbnbb7.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
@Setter
@NoArgsConstructor
public class ProfileResponse {

    private Long id;

    private String profileName;

    private String profileContact;

    private Long bookingsSize;

    private Long myAnnouncementSize;

    private Long onModerationSize;

    private List<ProfileBookingHouseResponse> bookings;

    public ProfileResponse(Long id, String profileName, String profileContact) {
        this.id = id;
        this.profileName = profileName;
        this.profileContact = profileContact;
    }

    public void addBookings(ProfileBookingHouseResponse profileBookingHouseResponse) {
        if (bookings == null) bookings = new ArrayList<>();
        bookings.add(profileBookingHouseResponse);
    }

}
