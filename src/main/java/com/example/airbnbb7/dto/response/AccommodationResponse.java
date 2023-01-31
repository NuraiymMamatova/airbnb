package com.example.airbnbb7.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationResponse {
    private String name;
    private String description;
    private LocationResponse address;
    private String image;

    public AccommodationResponse(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
