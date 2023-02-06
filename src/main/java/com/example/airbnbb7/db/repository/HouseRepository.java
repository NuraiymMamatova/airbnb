package com.example.airbnbb7.db.repository;

import com.example.airbnbb7.db.entities.House;
import com.example.airbnbb7.dto.response.HouseResponse;
import com.example.airbnbb7.dto.response.HouseResponseSortedPagination;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {

   @Query("select new com.example.airbnbb7.dto.response.HouseResponse(h.id, h.countOfBookedUser, h.title, h.price) from House h order by h.dateHouseCreated desc")
   List<HouseResponse> getLatestAccommodation();

    @Query("select new com.example.airbnbb7.dto.response.HouseResponse(h.id, h.countOfBookedUser,h.title,h.price) from House h ORDER BY h.rating DESC, h.countOfBookedUser DESC")
    List<Property> findAllByOrderByRatingDescBookingsDesc();

   @Query("select new com.example.airbnbb7.dto.response.HouseResponse(h.id,h.countOfBookedUser,h.title,h.price ) from House h where h.houseType = 1 and h.countOfBookedUser is not null  order by h.countOfBookedUser desc")
   List<HouseResponse> getPopularHouse();

   @Query("select new com.example.airbnbb7.dto.response.HouseResponse(h.id, h.title, h.descriptionOfListing) from House h where h.houseType = 0 and h.countOfBookedUser is not null order by h.countOfBookedUser desc")
   List<HouseResponse> getPopularApartment();

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
