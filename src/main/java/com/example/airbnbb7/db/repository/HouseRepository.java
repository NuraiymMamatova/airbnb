package com.example.airbnbb7.db.repository;

import com.example.airbnbb7.db.entities.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.time.LocalDate;

public interface HouseRepository extends JpaRepository<House, Long> {


    @Transactional
    @Modifying
    @Query(value = "insert into houses(id, price, title, max_of_guests, date_House_Created, houses_status, owner_id)" +
            "values (nextval('house_seq'), ?, ?, ?, ?, ?, ?)", nativeQuery = true)
    void saveHouse(Double price, String title, Long maxOfGuests, LocalDate date_House_Created, int houses_status, int owner_id);

    @Query("select max(h.id) from House h")
    Long houseMaxId();


}
