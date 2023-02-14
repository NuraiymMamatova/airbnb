package com.example.airbnbb7.db.repository;

import com.example.airbnbb7.db.entities.House;
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


        @Query("select new com.example.airbnbb7.dto.response.HouseResponseSortedPagination(h.id, h.price, h.title, h.descriptionOfListing, h.maxOfGuests, h.houseType, h.isFavorite) from House h where upper(h.location.townOrProvince) like :word or upper(h.location.address) like :word or upper(h.location.region) like :word")
//    @Query("select new com.example.airbnbb7.dto.response.HouseResponseSortedPagination(h.id, h.price, h.title, h.descriptionOfListing, h.maxOfGuests, h.houseType, h.isFavorite) from House h where upper(h.location.region) like :word and upper(h.location.townOrProvince) like :word or upper(h.location.region) like :word and upper(h.location.address) like :word")
    List<HouseResponseSortedPagination> searchNearby(String word);

    @Query("select new com.example.airbnbb7.dto.response.HouseResponseSortedPagination(h.id, h.price, h.title, h.descriptionOfListing, h.maxOfGuests, h.houseType, h.isFavorite) from House h where upper(h.location.region) like upper(:region) and upper(h.location.address) like upper(:word) or upper(h.location.region) like upper('bishkek') and upper(h.location.townOrProvince) like upper('bakay-ata')")
    List<HouseResponseSortedPagination> searchNearby(String word, String region);

    @Query("select h from House h where upper(h.location.region) like :region")
    List<House> getALlByRegion(String region);

}
