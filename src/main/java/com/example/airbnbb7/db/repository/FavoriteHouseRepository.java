package com.example.airbnbb7.db.repository;

import com.example.airbnbb7.db.entities.FavoriteHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteHouseRepository extends JpaRepository<FavoriteHouse, Long> {

    @Query("select f.id from FavoriteHouse f where f.house.id = :houseId")
    List<Long> findFavoriteHouseIdByHouseId(Long houseId);

}