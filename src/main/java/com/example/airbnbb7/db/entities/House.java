package com.example.airbnbb7.db.entities;

import com.example.airbnbb7.db.enums.HouseType;
import com.example.airbnbb7.db.enums.HousesBooked;
import com.example.airbnbb7.db.enums.HousesStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@Table(name = "houses")
@AllArgsConstructor
@NoArgsConstructor
public class House {

    @Id
    @SequenceGenerator(name = "house_gen", sequenceName = "house_seq", allocationSize = 1, initialValue = 21)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "house_gen")
    private Long id;

    @Column(nullable = false)
    private Double price;

    @Column(length = 1000000, columnDefinition = "text")
    private String title;

    @Column(length = 1000000, columnDefinition = "text")
    private String descriptionOfListing;

    @ElementCollection(fetch = LAZY)
    @Column(columnDefinition = "text", length = 1000000)
    private List<String> images;

    @Column(nullable = false)
    private Long maxOfGuests;

    private Long bookings;

    private HouseType houseType;

    private HousesStatus housesStatus;

    private HousesBooked housesBooked;

    private LocalDate dateHouseCreated;

    @OneToOne(cascade = ALL, fetch = EAGER, mappedBy = "house")
    private Location location;

    @OneToMany(cascade = {MERGE, REFRESH, DETACH, REMOVE}, mappedBy = "house", fetch = LAZY)
    private List<Booking> bookingDates;

    @OneToMany(cascade = {MERGE, REFRESH, DETACH, REMOVE}, mappedBy = "house")
    private List<Feedback> feedbacks;

    @ManyToOne(cascade = {MERGE, REFRESH, DETACH})
    private User owner;

    private Boolean watchedOrNot = false;

    public House(Double price, String title, String descriptionOfListing, Long maxOfGuests, List<String> images, HouseType houseType) {
        this.price = price;
        this.title = title;
        this.descriptionOfListing = descriptionOfListing;
        this.images = images;
        this.maxOfGuests = maxOfGuests;
        this.houseType = houseType;

    }

    public void addBooking(Booking booking) {
        if (bookingDates == null)
            bookingDates = new ArrayList<>();
        bookingDates.add(booking);
    }

}
