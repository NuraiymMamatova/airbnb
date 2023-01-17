package com.example.airbnbb7.db.repository;

import com.example.airbnbb7.db.entities.House;
import com.example.airbnbb7.db.enums.HousesStatus;
import com.example.airbnbb7.dto.response.HouseResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HouseRepository extends JpaRepository<House, Long> {

    @Query("update House h set h.housesStatus=:housesStatus where h.id =:id")
    HouseResponse updateHouseStatus(Long id, HousesStatus housesStatus);





}
