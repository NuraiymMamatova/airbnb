package com.example.airbnbb7.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.CascadeType.*;


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

    @OneToOne(cascade = {MERGE, DETACH, PERSIST, REFRESH})
    private House house;
    @ManyToOne(cascade = {MERGE, DETACH, PERSIST, REFRESH})
    private User user;
}
