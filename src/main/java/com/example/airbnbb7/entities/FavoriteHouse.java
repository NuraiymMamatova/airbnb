package com.example.airbnbb7.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "favorite_houses")

public class FavoriteHouse {

    @Id
    @SequenceGenerator(name = "favorite_houses_gen", sequenceName = "favorite_houses_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "favorite_houses_gen")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private House house;
    @OneToOne(cascade = CascadeType.ALL)
    private User user;
}
