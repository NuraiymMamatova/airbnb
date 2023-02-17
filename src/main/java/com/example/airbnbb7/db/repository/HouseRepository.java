package com.example.airbnbb7.db.repository;

import com.example.airbnbb7.db.entities.House;
import com.example.airbnbb7.dto.response.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface HouseRepository extends JpaRepository<House, Long> {

    @Query("select new com.example.airbnbb7.dto.response.AccommodationResponse(h.id, h.title, h.descriptionOfListing) from House h where h.housesStatus = 2 order by h.dateHouseCreated desc")
    List<AccommodationResponse> getLatestAccommodation();

    @Query("select new com.example.airbnbb7.dto.response.AccommodationResponse(h.id, h.price, h.title, h.descriptionOfListing) from House h where h.houseType = 1 and h.housesStatus = 2 order by h.bookings desc")
    List<AccommodationResponse> getPopularHouse();

    @Query("select new com.example.airbnbb7.dto.response.AccommodationResponse(h.id, h.title, h.descriptionOfListing) from House h where h.houseType = 0 and h.housesStatus = 2 order by h.bookings desc")
    List<AccommodationResponse> getPopularApartment();

    @Query("select new com.example.airbnbb7.dto.response.AnnouncementResponseForVendor(h.id, h.title, h.descriptionOfListing, h.maxOfGuests, h.houseType) from House h where h.id = :houseId")
    Optional<AnnouncementResponseForVendor> findHouseByIdForVendor(Long houseId);

    @Query("select new com.example.airbnbb7.dto.response.AnnouncementResponseForUser(h.id, h.title, h.descriptionOfListing, h.maxOfGuests, h.houseType) from House h where h.id = :houseId")
    Optional<AnnouncementResponseForUser> findHouseByIdForUser(Long houseId);

    @Query("select new com.example.airbnbb7.dto.response.AnnouncementResponseForAdmin(h.id, h.title, h.descriptionOfListing, h.maxOfGuests, h.houseType) from House h where h.id = :houseId")
    Optional<AnnouncementResponseForAdmin> findHouseByIdForAdmin(Long houseId);

    @Query(value = "select images from house_images  where house_id = :houseId", nativeQuery = true)
    List<String> findImagesByHouseId(Long houseId);

    @Query("select new com.example.airbnbb7.dto.response.HouseResponseSortedPagination(h.id," +
            "h.price," +
            "h.title," +
            "h.descriptionOfListing," +
            "h.maxOfGuests," +
            "h.houseType,h.isFavorite) from House h where upper(h.title) like upper(concat('%',:search, '%')) and h.housesStatus = 2 or upper(h.location.region) like  upper(concat('%',:search,'%')) and h.housesStatus =2 or upper( h.location.townOrProvince) like upper( concat('%',:search,'%')) and h.housesStatus =2 or upper( h.location.address) like upper( concat('%',:search,'%')) and h.housesStatus =2")
    Page<HouseResponseSortedPagination> pagination(String search, Pageable pageable);

    @Query("select new com.example.airbnbb7.dto.response.HouseResponseSortedPagination(h.id," +
            "h.price," +
            "h.title," +
            "h.descriptionOfListing," +
            "h.maxOfGuests," +
            "h.houseType,h.isFavorite) from House h where h.housesStatus = 2")
    Page<HouseResponseSortedPagination> getAllResponse(Pageable pageable);

    @Query("select count(h) from House h where h.location.region = :region")
    Long count(String region);

}
