package com.example.airbnbb7.db.service;

import com.example.airbnbb7.db.entities.User;
import com.example.airbnbb7.db.customClass.SimpleResponse;
import com.example.airbnbb7.dto.response.HouseResponseSortedPagination;

import java.util.List;

public interface FavoriteHouseService {

    SimpleResponse saveFavoriteHouse(Long houseId, User user);

    List<HouseResponseSortedPagination> getAllFavoriteHouseByUserId(Long userId);
}
