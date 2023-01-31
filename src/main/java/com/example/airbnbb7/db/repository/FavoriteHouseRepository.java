package com.example.airbnbb7.db.repository;

import com.example.airbnbb7.db.entities.FavoriteHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteHouseRepository extends JpaRepository<FavoriteHouse,Long> {
}
