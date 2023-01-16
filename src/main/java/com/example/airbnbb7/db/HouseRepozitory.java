package com.example.airbnbb7.db;

import com.example.airbnbb7.db.entities.House;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseRepozitory extends JpaRepository<House, Long> {

}
