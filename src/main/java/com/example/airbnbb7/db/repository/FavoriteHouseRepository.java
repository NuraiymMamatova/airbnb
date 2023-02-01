package com.example.airbnbb7.db.repository;

import com.example.airbnbb7.db.entities.FavoriteHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteHouseRepository extends JpaRepository<FavoriteHouse,Long> {

//    @Query("select new com.example.airbnbb7.dto.response.HouseResponseSortedPagination(h.id,h.price,h.title,h.descriptionOfListing,h.maxOfGuests,h.houseType) from House h where ")
//    List<HouseResponseSortedPagination> getAllFavoriteHouse();

    @Query("select f.id from FavoriteHouse f where f.house.id = :houseId and f.user.id = :userId")
    Long getIdFavoriteHouseByHouseIdByUserId(Long houseId, Long userId);
}
