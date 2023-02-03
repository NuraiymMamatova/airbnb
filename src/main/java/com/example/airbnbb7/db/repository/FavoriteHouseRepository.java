package com.example.airbnbb7.db.repository;

import com.example.airbnbb7.db.entities.FavoriteHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteHouseRepository extends JpaRepository<FavoriteHouse, Long> {

    @Query("select f from FavoriteHouse f where f.house.id = :houseId and f.user.id = :userId")
    FavoriteHouse getFavoriteHouseByHouseIdByUserId(Long houseId, Long userId);
}
