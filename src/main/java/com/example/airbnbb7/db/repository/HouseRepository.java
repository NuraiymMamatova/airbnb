package com.example.airbnbb7.db.repository;

import com.example.airbnbb7.db.entities.House;
import com.example.airbnbb7.dto.response.AccommodationResponse;
import com.example.airbnbb7.dto.response.AnnouncementResponseForAdmin;
import com.example.airbnbb7.dto.response.AnnouncementResponseForUser;
import com.example.airbnbb7.dto.response.AnnouncementResponseForVendor;
import com.example.airbnbb7.dto.response.HouseResponseSortedPagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HouseRepository extends JpaRepository<House, Long> {

    @Query("select new com.example.airbnbb7.dto.response.AccommodationResponse(h.id, h.bookings, h.title, h.price) from House h order by h.dateHouseCreated desc")
    List<AccommodationResponse> getLatestAccommodation();

    @Query("select new com.example.airbnbb7.dto.response.AccommodationResponse(h.id, h.bookings, h.title, h.descriptionOfListing) from House h where h.houseType = 1 and h.bookings = (select max(h.bookings) from House h where h.houseType = 1)")
    List<AccommodationResponse> getPopularHouse();

    @Query("select new com.example.airbnbb7.dto.response.AccommodationResponse(h.id, h.bookings, h.title, h.descriptionOfListing) from House h where h.houseType = 0 and h.bookings = (select max(h.bookings) from House h where h.houseType = 0)")
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
            "h.houseType,h.isFavorite) from House h where upper(h.title) like upper(concat('%',:search, '%')) or upper(h.location.region) like  upper(concat('%',:search,'%')) or upper( h.location.townOrProvince) like upper( concat('%',:search,'%')) or upper( h.location.address) like upper( concat('%',:search,'%'))")
    Page<HouseResponseSortedPagination> pagination(String search, Pageable pageable);

    @Query("select new com.example.airbnbb7.dto.response.HouseResponseSortedPagination(h.id," +
            "h.price," +
            "h.title," +
            "h.descriptionOfListing," +
            "h.maxOfGuests," +
            "h.houseType,h.isFavorite) from House h")
    Page<HouseResponseSortedPagination> getAllResponse(Pageable pageable);

    @Query("select new com.example.airbnbb7.dto.response.HouseResponseSortedPagination(" +
            "h.id," +
            "h.price," +
            "h.title," +
            "h.descriptionOfListing," +
            "h.maxOfGuests," +
            "h.houseType,h.isFavorite) from House h where h.location.region = :region ")
    List<HouseResponseSortedPagination> regionHouses(String region, Pageable pageable);

    @Query("select count(h) from House h where h.location.region = :region")
    Long count(String region);

}
