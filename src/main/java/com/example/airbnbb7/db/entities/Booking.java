package com.example.airbnbb7.db.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "booking_dates")
public class Booking {

    @Id
    @SequenceGenerator(name = "booking_dates_gen", sequenceName = "booking_dates_seq", allocationSize = 1, initialValue = 6)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_dates_gen")
    private Long id;

    private Double price;

    private LocalDate checkIn;

    private LocalDate checkOut;

    @ManyToMany(cascade = {MERGE, REFRESH, DETACH})
    private List<User> users;

    @ManyToOne(cascade = {MERGE, REFRESH, DETACH})
    private House house;

}
