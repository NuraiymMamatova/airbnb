package com.example.airbnbb7.db.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import static jakarta.persistence.CascadeType.*;


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

    private Long countOfBookedHouse;

    @ManyToMany(cascade = ALL, mappedBy = "guests")
    private List<House> houses;

    @ManyToMany(cascade = ALL, mappedBy = "users")
    private List<Booking> bookings;

    @ManyToMany(targetEntity = Role.class, cascade = {REFRESH, DETACH, MERGE, PERSIST}, mappedBy = "users")
    private List<Role> roles;

    @OneToMany(cascade = ALL)
    private List<FavoriteHouse> favoriteHouses;


}
