package com.example.airbnbb7.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteHouseResponse {

    private Long id;

    private HouseResponseForVendor houseResponsesForVendor;

    private UserResponseForVendor userResponsesForVendor;
}
