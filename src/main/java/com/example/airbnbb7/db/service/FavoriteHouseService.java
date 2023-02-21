package com.example.airbnbb7.db.service;

import com.example.airbnbb7.db.customClass.SimpleResponse;
import com.example.airbnbb7.dto.response.HouseResponseSortedPagination;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface FavoriteHouseService {

    SimpleResponse saveFavoriteHouse(Long houseId, Authentication authentication);

    List<HouseResponseSortedPagination> getAllFavoriteHouseByUserId(Authentication authentication);
}
