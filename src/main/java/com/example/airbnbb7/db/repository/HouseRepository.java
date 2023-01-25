package com.example.airbnbb7.db.repository;

import com.example.airbnbb7.db.entities.House;
import com.example.airbnbb7.db.enums.HouseType;
import com.example.airbnbb7.dto.response.HouseResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {

    @Query("select new com.example.airbnbb7.dto.response.HouseResponse(h.id," +
            "h.price," +
            "h.title," +
            "h.descriptionOfListing," +
            "h.maxOfGuests," +
            "h.houseType) from House h where upper(h.title) like concat('%',:pagination, '%')")
    List<HouseResponse> pagination(@Param("pagination") String pagination, Pageable pageable);

    @Query("select new com.example.airbnbb7.dto.response.HouseResponse(h.id," +
            "h.price," +
            "h.title," +
            "h.descriptionOfListing," +
            "h.maxOfGuests," +
            "h.houseType) from House h where h.houseType = 1 ")
    List<HouseResponse> getAllHouses();

    @Query("select new com.example.airbnbb7.dto.response.HouseResponse(h.id," +
            "h.price," +
            "h.title," +
            "h.descriptionOfListing," +
            "h.maxOfGuests," +
            "h.houseType) from House h where h.houseType = 0 ")
    List<HouseResponse> getAllApartments();

    @Query("select new com.example.airbnbb7.dto.response.HouseResponse(" +
            "h.id," +
            "h.price," +
            "h.title," +
            "h.descriptionOfListing," +
            "h.maxOfGuests," +
            "h.houseType) from House h where h.location.region = :region ")
    List<HouseResponse> regionHouses(String region);

    @Query("select new com.example.airbnbb7.dto.response.HouseResponse(h.id," +
            "h.price," +
            "h.title," +
            "h.descriptionOfListing," +
            "h.maxOfGuests," +
            "h.houseType) from House h where h.id = :id")
    List<HouseResponse> convertToResponseById(Long id);


    @Query("select new com.example.airbnbb7.dto.response.HouseResponse(h.id," +
            "h.price," +
            "h.title," +
            "h.descriptionOfListing," +
            "h.maxOfGuests," +
            "h.houseType) from House h")
    List<HouseResponse> siuu();
}
