package com.example.airbnbb7.dto.response;

import lombok.AllArgsConstructor;
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

    private List<HouseResponse> myAnnouncement;

    private List<HouseResponse> onModeration;

    public ProfileResponse(Long id, String profileName, String profileContact) {
        this.id = id;
        this.profileName = profileName;
        this.profileContact = profileContact;
    }

    public void addBookings(ProfileBookingHouseResponse profileBookingHouseResponse) {
        if (bookings == null) bookings = new ArrayList<>();
        bookings.add(profileBookingHouseResponse);
    }

    public void addMyAnnouncement(HouseResponse houseResponse) {
        if (myAnnouncement == null) myAnnouncement = new ArrayList<>();
        myAnnouncement.add(houseResponse);
    }

    public void addOnModeration(HouseResponse houseResponse) {
        if (onModeration == null) onModeration = new ArrayList<>();
        onModeration.add(houseResponse);
    }

}
