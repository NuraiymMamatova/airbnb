package com.example.airbnbb7.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.LAZY;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @SequenceGenerator(name = "user_gen", sequenceName = "user_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_gen")
    private Long id;

    private String name;

    private String email;

    private String password;

    private String image;

    private Boolean block = false;

    private Long countOfBookedHouse;

    private LocalDate addHouseToFavoriteHouses;

    @ManyToMany(cascade = ALL, fetch = LAZY, mappedBy = "guests")
    private List<House> houses;

    @ManyToMany(cascade = ALL, fetch = LAZY, mappedBy = "users")
    private List<Booking> bookings;

    @ManyToMany(cascade ={REFRESH, DETACH, MERGE,PERSIST}, fetch = LAZY, mappedBy = "users")
    private List<Role> roles;

    @OneToMany(cascade = ALL, fetch = LAZY)
    private List<FavoriteHouse> favoriteHouses;


}
