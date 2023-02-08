package com.example.airbnbb7.db.repository;

import com.example.airbnbb7.db.entities.House;
import com.example.airbnbb7.dto.response.HouseResponseSortedPagination;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HouseRepository extends JpaRepository<House, Long> {

    @Query("select new com.example.airbnbb7.dto.response.HouseResponseSortedPagination(h.id," +
            "h.price," +
            "h.title," +
            "h.descriptionOfListing," +
            "h.maxOfGuests," +
            "h.houseType,h.isFavorite) from House h where upper(h.title) like concat('%',:pagination, '%') or h.title like concat('%',:search,'%') or h.location.region like concat('%',:search,'%') or h.location.townOrProvince like concat('%',:search,'%') or h.location.address like concat('%',:search,'%')")
    List<HouseResponseSortedPagination> pagination(@Param("pagination") String pagination,String search, Pageable pageable);

    @Query("select new com.example.airbnbb7.dto.response.HouseResponseSortedPagination(h.id," +
            "h.price," +
            "h.title," +
            "h.descriptionOfListing," +
            "h.maxOfGuests," +
            "h.houseType,h.isFavorite) from House h where h.houseType = 1 ")
    List<HouseResponseSortedPagination> getAllHouses(Pageable pageable);

    @Query("select new com.example.airbnbb7.dto.response.HouseResponseSortedPagination(h.id," +
            "h.price," +
            "h.title," +
            "h.descriptionOfListing," +
            "h.maxOfGuests," +
            "h.houseType,h.isFavorite) from House h where h.houseType = 0 ")
    List<HouseResponseSortedPagination> getAllApartments(Pageable pageable);

    @Query("select new com.example.airbnbb7.dto.response.HouseResponseSortedPagination(" +
            "h.id," +
            "h.price," +
            "h.title," +
            "h.descriptionOfListing," +
            "h.maxOfGuests," +
            "h.houseType,h.isFavorite) from House h where h.location.region = :region ")
    List<HouseResponseSortedPagination> regionHouses(String region, Pageable pageable);
}
