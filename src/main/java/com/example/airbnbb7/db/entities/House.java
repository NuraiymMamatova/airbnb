package com.example.airbnbb7.db.entities;

import com.example.airbnbb7.db.enums.HouseType;
import com.example.airbnbb7.db.enums.HousesBooked;
import com.example.airbnbb7.db.enums.HousesStatus;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.LAZY;


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

    private Long countOfBookedUser;

    private HouseType houseType;

    private HousesStatus housesStatus;

    private HousesBooked housesBooked;

    private LocalDate dateHouseCreated;


    @OneToOne(cascade = ALL,mappedBy = "house")
    private Location location;

    @OneToMany(cascade = {MERGE, REFRESH, DETACH, REMOVE}, mappedBy = "house")
    private List<Booking> bookingDates;

    @ManyToMany(cascade = {MERGE, REFRESH, DETACH})
    @JoinTable(name = "house_users", joinColumns = @JoinColumn(name = "house_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> guests;

    @OneToMany(cascade = {MERGE, REFRESH, DETACH, REMOVE}, mappedBy = "house")
    private List<Feedback> feedbacks;

    @OneToOne(cascade = {MERGE, REFRESH, DETACH})
    private User owner;
}
