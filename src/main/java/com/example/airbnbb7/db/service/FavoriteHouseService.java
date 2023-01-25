package com.example.airbnbb7.db.service;

public interface FavoriteHouseService {

    void deleteFavoriteHouseByHouseId(Long houseId);

    void addHouseToFavorite(Long houseId, Long userId);
}
