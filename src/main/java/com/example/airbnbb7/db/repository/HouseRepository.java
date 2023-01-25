package com.example.airbnbb7.db.repository;

import com.example.airbnbb7.db.entities.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {

            List<House> findHouseById();
}