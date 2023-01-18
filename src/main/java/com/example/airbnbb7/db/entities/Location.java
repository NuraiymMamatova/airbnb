package com.example.airbnbb7.db.entities;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static javax.persistence.CascadeType.*;


@Entity
@Setter
@Getter
@NoArgsConstructor
public class Location {

    @Id
    @SequenceGenerator(name = "location_gen", sequenceName = "location_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "location_gen")
    private Long id;

    @Column(name = "town_or_province", nullable = false)
    private String townOrProvince;

    private String address;

    private String region;

    @OneToOne(cascade = {REFRESH, MERGE, DETACH})
    private House house;
}