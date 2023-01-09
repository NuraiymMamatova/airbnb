package com.example.airbnbb7.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.EAGER;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "booking_dates")
public class Booking {

    @Id
    @SequenceGenerator(name = "booking_dates_gen", sequenceName = "booking_dates_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_dates_gen")
    private Long id;

    private Double price;

    private LocalDate checkIn;

    private LocalDate checkOut;

    @ManyToOne(cascade = {MERGE, REFRESH, DETACH, PERSIST}, fetch = EAGER)
    private User user;

    @ManyToOne(cascade = {MERGE, REFRESH, DETACH, PERSIST}, fetch = EAGER)
    private House house;
}
