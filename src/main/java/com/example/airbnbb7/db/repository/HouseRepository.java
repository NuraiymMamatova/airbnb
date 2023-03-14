package com.example.airbnbb7.db.repository;

import com.example.airbnbb7.db.entities.House;
import com.example.airbnbb7.dto.response.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface HouseRepository extends JpaRepository<House, Long> {

    @Query("select new com.example.airbnbb7.dto.response.AccommodationResponse(h.id, h.title, h.descriptionOfListing) from House h where h.housesStatus = 2 order by h.dateHouseCreated desc")
    List<AccommodationResponse> getLatestAccommodation();

    @Query("select new com.example.airbnbb7.dto.response.AccommodationResponse(h.id, h.price, h.title, h.descriptionOfListing) from House h where h.houseType = 1 and h.housesStatus = 2 order by h.bookings desc")
    List<AccommodationResponse> getPopularHouse();

    @Query("select h from House h where h.housesStatus = 2 order by h.bookings desc")
    List<House> getPopular();

    @Query("select new com.example.airbnbb7.dto.response.AccommodationResponse(h.id, h.title, h.descriptionOfListing) from House h where h.houseType = 0 and h.housesStatus = 2 order by h.bookings desc")
    List<AccommodationResponse> getPopularApartment();

    @Query("select new com.example.airbnbb7.dto.response.AnnouncementResponseForVendor(h.id, h.title, h.descriptionOfListing, h.maxOfGuests, h.houseType) from House h where h.id = :houseId")
    Optional<AnnouncementResponseForVendor> findHouseByIdForVendor(Long houseId);

    @Query("select new com.example.airbnbb7.dto.response.AnnouncementResponseForUser(h.id, h.title, h.price, h.descriptionOfListing, h.maxOfGuests, h.houseType) from House h where h.id = :houseId")
    Optional<AnnouncementResponseForUser> findHouseByIdForUser(Long houseId);

    @Query("select new com.example.airbnbb7.dto.response.AnnouncementResponseForAdmin(h.id, h.title, h.descriptionOfListing, h.maxOfGuests, h.houseType) from House h where h.id = :houseId")
    Optional<AnnouncementResponseForAdmin> findHouseByIdForAdmin(Long houseId);

    @Query("select new com.example.airbnbb7.dto.response.HouseResponseSortedPagination(h.id, h.price, h.title,h.descriptionOfListing, h.maxOfGuests, h.houseType, h.isFavorite) from House h where h.id = :houseId")
    Optional<HouseResponseSortedPagination> findHouseById(Long houseId);

    @Query(value = "select images from house_images  where house_id = :houseId", nativeQuery = true)
    List<String> findImagesByHouseId(Long houseId);

    @Query("select new com.example.airbnbb7.dto.response.HouseResponseSortedPagination(h.id," +
            "h.price," +
            "h.title," +
            "h.descriptionOfListing," +
            "h.maxOfGuests," +
            "h.houseType,h.isFavorite) from House h where h.housesStatus = 2")
    List<HouseResponseSortedPagination> getAllResponse();

    @Query("select h from House h where h.housesStatus = 2")
    List<House> findAllAnnouncements();

    @Query("select new com.example.airbnbb7.dto.response.HouseResponseForAdmin(" +
            "h.id," +
            "h.price," +
            "h.title," +
            "h.descriptionOfListing," +
            "h.maxOfGuests," +
            "h.houseType," +
            "h.watchedOrNot) from House h where h.housesStatus = 3")
    List<HouseResponseForAdmin> getAllStatusOfTheWholeHouseOnModeration();

    @Query("select h.id from House h where h.owner.id =:userId")
    List<Long> getAllHouseIdByUserId(Long userId);

    @Query("select new com.example.airbnbb7.dto.response.HouseResponseSortedPagination(h.id," +
            "h.price," +
            "h.title," +
            "h.descriptionOfListing," +
            "h.maxOfGuests," +
            "h.houseType,h.isFavorite) from House h where upper(h.title) like upper(concat('%',:search, '%')) and h.housesStatus = 2 or upper(h.location.region) like  " +
            "upper(concat('%',:search,'%')) and h.housesStatus = 2 or upper( h.location.townOrProvince) " +
            "like upper( concat('%',:search,'%')) and h.housesStatus = 2 or upper( h.location.address) like upper( concat('%',:search,'%')) " +
            "and h.housesStatus = 2")
    List<HouseResponseSortedPagination> searchByQuery(@Param("search") String search);

    @Query(value = "select owner_id from houses where id = (select house_id from house_images where image_id = :imageId limit 1)", nativeQuery = true)
    Long getUserIdByImageId(Long imageId);

    @Modifying
    @Transactional
    @Query(value = "delete from house_images where image_id = :imageId" , nativeQuery = true)
    void deleteImageById(Long imageId);

}
