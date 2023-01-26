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
public class UserResponseForVendor {

    private Long id;

    private String name;

    private String email;

    private String image;

    private LocalDate addedHouseToFavorites;

}
