package com.example.airbnbb7.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LocationResponse {
    private Long id;
    private String townOrProvince;
    private String address;
    private String region;

    @Override
    public String toString() {
        return "LocationResponse{" +
                "id=" + id +
                ", townOrProvince='" + townOrProvince + '\'' +
                ", address='" + address + '\'' +
                ", region='" + region + '\'' +
                '}';
    }
}
