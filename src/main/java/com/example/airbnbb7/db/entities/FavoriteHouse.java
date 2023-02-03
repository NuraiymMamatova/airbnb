package com.example.airbnbb7.db.entities;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "favorite_houses")
public class FavoriteHouse {

    @Id
    @SequenceGenerator(name = "favorite_houses_gen", sequenceName = "favorite_houses_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "favorite_houses_gen")
    private Long id;

    @ManyToOne(cascade = {MERGE, DETACH, REFRESH})
    private House house;

    @ManyToOne(cascade = {MERGE, DETACH, REFRESH})
    private User user;

    public FavoriteHouse(House house, User user) {
        this.house = house;
        this.user = user;
    }
}
