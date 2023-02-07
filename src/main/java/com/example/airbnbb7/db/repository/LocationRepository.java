package com.example.airbnbb7.db.repository;


import com.example.airbnbb7.db.entities.Location;
import com.example.airbnbb7.dto.response.LocationResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {

    @Query("select new com.example.airbnbb7.dto.response.LocationResponse(l.id, l.townOrProvince, l.address, l.region) from Location l where l.house.id = :houseId")
    Optional<LocationResponse> findLocationByHouseId(Long houseId);

    @Query("select new com.example.airbnbb7.dto.response.LocationResponse(l.id, l.townOrProvince, l.address, l.region) from Location l where l.id = :locationId")
    Optional<LocationResponse> findLocationById(Long locationId);

    @Transactional
    @Modifying
    @Query(value = "insert into locations(id, address, region, town_or_province, house_id)" +
            "values (nextval('location_seq'), ?, ?, ?, ?)", nativeQuery = true)
    void saveLocation(String address, String region, String town_or_province, int house_id);

    @Query("select max(l.id) from  Location  l")
    Long locationMaxId();

    @Query("select new com.example.airbnbb7.dto.response.LocationResponse(l.id,l.townOrProvince,l.address,l.region) from Location l where l = :location")
    LocationResponse convertToResponse(Location location);

}
