package com.example.airbnbb7.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {

    private Double price;

    private LocalDate checkIn;

    private LocalDate checkOut;
}
