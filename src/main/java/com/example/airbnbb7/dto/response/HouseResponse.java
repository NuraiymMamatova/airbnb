package com.example.airbnbb7.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HouseResponse {
    private Long id;

    private String name;

    private double rating;

    private List<String> images;

    private Double price;

    private LocationResponse locationResponse;

    private Long countOfBookedUser;

    public HouseResponse(Long id,Long countOfBookedUser,String name, Double price) {
        this.id = id;
        this.countOfBookedUser = countOfBookedUser;
        this.name = name;
        this.price = price;
    }
}
