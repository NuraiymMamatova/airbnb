package com.example.airbnbb7.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationResponseForAdmin {

    private List<HouseResponseForAdmin> houseResponseForAdmins;

    private Long page;

    private int pageSize;
}
