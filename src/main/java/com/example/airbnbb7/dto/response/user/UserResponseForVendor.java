package com.example.airbnbb7.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseForVendor {

    private Long id;

    private String name;

    private String email;

    private String image;

    private LocalDate addedHouseToFavorites;

}
