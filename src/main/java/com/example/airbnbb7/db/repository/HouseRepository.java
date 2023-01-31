package com.example.airbnbb7.db.repository;

import com.example.airbnbb7.db.entities.House;
import com.example.airbnbb7.dto.response.AccommodationResponse;
import com.example.airbnbb7.dto.response.HouseResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {
   @Query("select new com.example.airbnbb7.dto.response.AccommodationResponse(h.title,h.descriptionOfListing) from House h order by h.dateHouseCreated desc")
   List<AccommodationResponse> getLatestAccommodation(Pageable pageable);

   @Query("select new com.example.airbnbb7.dto.response.HouseResponse(h.id,h.countOfBookedUser,h.title,h.price ) from House h where h.houseType = 1 order by h.countOfBookedUser desc")
   List<HouseResponse> getPopularHouse(Pageable pageable);

   @Query("select new com.example.airbnbb7.dto.response.AccommodationResponse(h.title,h.descriptionOfListing) from House h where h.houseType = 0 order by h.countOfBookedUser desc")
   List<AccommodationResponse> getPopularApartment(Pageable pageable);
}