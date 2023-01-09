package com.example.airbnbb7.entities;

import com.example.airbnbb7.enums.HouseType;
import com.example.airbnbb7.enums.HousesBooked;
import com.example.airbnbb7.enums.HousesStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.FetchType.LAZY;


@Entity
@Getter
@Setter
@Table(name = "houses")
public class House {

    @Id
    @SequenceGenerator(name = "house_gen", sequenceName = "house_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "house_gen")
    private Long id;

    @Column(nullable = false)
    private Double price;

    private String title;

    private String descriptionOfListing;

    @ElementCollection(fetch = LAZY)
    private List<String> images;

    @Column(nullable = false)
    private Long maxOfGuests;

    private HouseType houseType;

    private HousesStatus housesStatus;
    private HousesBooked housesBooked;

    private LocalDate dateHouseCreated;


    @OneToOne(cascade = ALL, fetch = EAGER)
    private Location location;

    @OneToMany(cascade = ALL, fetch = LAZY, mappedBy = "house")
    private List<Booking> bookingDates;

    @ManyToMany(cascade = {MERGE, PERSIST, REFRESH, DETACH}, fetch = LAZY)
    @JoinTable(name = "house_users",
            joinColumns = @JoinColumn(name = "house_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> guests;

    @OneToMany(cascade = ALL, fetch = LAZY)
    private List<Feedback> feedbacks;

    @OneToOne(cascade = ALL)
    private User owner;
}
