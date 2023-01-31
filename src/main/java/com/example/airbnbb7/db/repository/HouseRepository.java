package com.example.airbnbb7.db.repository;

import com.example.airbnbb7.db.entities.House;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseRepository extends JpaRepository<House, Long> {

}