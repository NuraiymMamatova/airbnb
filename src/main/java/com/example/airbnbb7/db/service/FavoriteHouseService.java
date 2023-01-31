package com.example.airbnbb7.db.service;

public interface FavoriteHouseService {

    void saveFavoriteHouse(Long houseId,Long userId);

    void deleteFavoriteHouse(Long id);

}
