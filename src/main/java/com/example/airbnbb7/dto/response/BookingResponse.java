package com.example.airbnbb7.dto.response;

import com.example.airbnbb7.dto.response.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
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
