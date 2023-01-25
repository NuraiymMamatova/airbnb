package com.example.airbnbb7.dto.response;

import com.example.airbnbb7.dto.response.house.HouseResponseForVendor;
import com.example.airbnbb7.dto.response.user.UserResponseForVendor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteHouseResponse {

    private Long id;

    private HouseResponseForVendor houseResponsesForVendor;

    private UserResponseForVendor userResponsesForVendor;
}
