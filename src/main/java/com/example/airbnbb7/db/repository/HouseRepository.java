package com.example.airbnbb7.db.repository;

import com.example.airbnbb7.db.entities.House;
import com.example.airbnbb7.dto.response.HouseResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {

    @Query("select new com.example.airbnbb7.dto.response.HouseResponse(h.id,h.price,h.title,h.descriptionOfListing,h.maxOfGuests,h.houseType) from House h where h.id = :id")
    HouseResponse convertToResponseById(@Param("id") Long id);

    @Query("select i from House i where upper(i.title) like concat('%',:pagination, '%')")
    List<House> searchPagination(@Param("pagination") String pagination, Pageable pageable);

}
