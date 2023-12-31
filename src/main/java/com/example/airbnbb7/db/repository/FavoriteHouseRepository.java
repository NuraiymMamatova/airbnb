package com.example.airbnbb7.db.repository;

import com.example.airbnbb7.db.entities.FavoriteHouse;
import com.example.airbnbb7.db.entities.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface FavoriteHouseRepository extends JpaRepository<FavoriteHouse, Long> {

    @Query("select f from FavoriteHouse f where f.house.id = :houseId and f.user.id = :userId")
    FavoriteHouse getFavoriteHouseByHouseIdByUserId(Long houseId, Long userId);

    @Query("select count(f) from FavoriteHouse f where f.house = :house")
    int getCountOfFavorite(House house);

    @Query("select f from FavoriteHouse f where f.house.id = :houseId")
    List<FavoriteHouse> getAllFavoriteHouseByHouseId(Long houseId);

    @Modifying
    @Transactional
    @Query("delete from FavoriteHouse f where f.house.id = :houseId")
    void deleteAllByHouseId(Long houseId);
}
