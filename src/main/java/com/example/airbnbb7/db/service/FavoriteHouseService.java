package com.example.airbnbb7.db.service;

import com.example.airbnbb7.dto.response.HouseResponseSortedPagination;

import java.util.List;

public interface FavoriteHouseService {

    void saveFavoriteHouse(Long houseId, Long userId);

    List<HouseResponseSortedPagination> getAllFavoriteHouseByUserId(Long userId);
}
