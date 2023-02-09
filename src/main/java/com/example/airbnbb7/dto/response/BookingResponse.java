package com.example.airbnbb7.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Getter
@Setter
@Component
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {

    private Long id;

    private Double price;

    private LocalDate checkIn;

    private LocalDate checkOut;

    private UserResponse owner;

    public BookingResponse(Long id, Double price, LocalDate checkIn, LocalDate checkOut) {
        this.id = id;
        this.price = price;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }
}
