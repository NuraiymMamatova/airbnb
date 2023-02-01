package com.example.airbnbb7.db.repository;

import com.example.airbnbb7.db.entities.House;
import com.example.airbnbb7.dto.response.HouseResponse;
import com.example.airbnbb7.dto.response.HouseResponseSortedPagination;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HouseRepository extends JpaRepository<House, Long> {


    @Transactional
    @Modifying
    @Query(value = "insert into houses(id, price, title, max_of_guests, date_House_Created, houses_status, owner_id)" +
            "values (nextval('house_seq'), ?, ?, ?, ?, ?, ?)", nativeQuery = true)
    void saveHouse(Double price, String title, Long maxOfGuests, LocalDate date_House_Created, int houses_status, int owner_id);

    @Query("select max(h.id) from House h")
    Long houseMaxId();

    @Query("select new com.example.airbnbb7.dto.response.HouseResponseSortedPagination(h.id," +
            "h.price," +
            "h.title," +
            "h.descriptionOfListing," +
            "h.maxOfGuests," +
            "h.houseType) from House h where upper(h.title) like concat('%',:pagination, '%')")
    List<HouseResponseSortedPagination> pagination(@Param("pagination") String pagination, Pageable pageable);

    @Query("select new com.example.airbnbb7.dto.response.HouseResponseSortedPagination(h.id," +
            "h.price," +
            "h.title," +
            "h.descriptionOfListing," +
            "h.maxOfGuests," +
            "h.houseType) from House h where h.houseType = 1 ")
    List<HouseResponseSortedPagination> getAllHouses(Pageable pageable);

    @Query("select new com.example.airbnbb7.dto.response.HouseResponseSortedPagination(h.id," +
            "h.price," +
            "h.title," +
            "h.descriptionOfListing," +
            "h.maxOfGuests," +
            "h.houseType) from House h where h.houseType = 0 ")
    List<HouseResponseSortedPagination> getAllApartments(Pageable pageable);

    @Query("select new com.example.airbnbb7.dto.response.HouseResponseSortedPagination(" +
            "h.id," +
            "h.price," +
            "h.title," +
            "h.descriptionOfListing," +
            "h.maxOfGuests," +
            "h.houseType) from House h where h.location.region = :region ")
    List<HouseResponseSortedPagination> regionHouses(String region, Pageable pageable);
}
