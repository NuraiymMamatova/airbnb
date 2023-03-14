package com.example.airbnbb7.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationResponse {

    private Long id;

    private String name;

    private double rating;

    private Map<Long, String> images;

    private String description;

    private Double price;

    private LocationResponse locationResponse;

    public AccommodationResponse(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public AccommodationResponse(Long id, Double price, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
