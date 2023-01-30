package com.example.airbnbb7.db.repository;

import com.example.airbnbb7.db.entities.House;
import com.example.airbnbb7.dto.response.AnnouncementResponseForAdmin;
import com.example.airbnbb7.dto.response.AnnouncementResponseForUser;
import com.example.airbnbb7.dto.response.AnnouncementResponseForVendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {

    @Query("select new com.example.airbnbb7.dto.response.AnnouncementResponseForVendor(h.id, h.title, h.descriptionOfListing, h.maxOfGuests, h.houseType) from House h where h.id = :houseId")
    Optional<AnnouncementResponseForVendor> findHouseByIdForVendor(Long houseId);

    @Query("select new com.example.airbnbb7.dto.response.AnnouncementResponseForUser(h.id, h.title, h.descriptionOfListing, h.maxOfGuests, h.houseType) from House h where h.id = :houseId")
    Optional<AnnouncementResponseForUser> findHouseByIdForUser(Long houseId);

    @Query("select new com.example.airbnbb7.dto.response.AnnouncementResponseForAdmin(h.id, h.title, h.descriptionOfListing, h.maxOfGuests, h.houseType) from House h where h.id = :houseId")
    Optional<AnnouncementResponseForAdmin> findHouseByIdForAdmin(Long houseId);

    @Query(value = "select images from house_images  where house_id = :houseId", nativeQuery = true)
    List<String> findImagesByHouseId(Long houseId);

}