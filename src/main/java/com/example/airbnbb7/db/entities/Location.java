package com.example.airbnbb7.db.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.CascadeType.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "locations")
@AllArgsConstructor
public class Location {

    @Id
    @SequenceGenerator(name = "location_gen", sequenceName = "location_seq", allocationSize = 1, initialValue = 6)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "location_gen")
    private Long id;

    @Column(name = "town_or_province", nullable = false)
    private String townOrProvince;

    private String address;

    private String region;

    @OneToOne(cascade = ALL, mappedBy = "location")
    private House house;

    public Location(String townOrProvince, String address, String region) {
        this.townOrProvince = townOrProvince;
        this.address = address;
        this.region = region;
    }
}
