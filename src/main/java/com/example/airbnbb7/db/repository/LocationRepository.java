package com.example.airbnbb7.db.repository;

import com.example.airbnbb7.db.entities.Location;
import com.example.airbnbb7.dto.response.LocationResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    @Query("select new com.example.airbnbb7.dto.response.LocationResponse(l.id, l.townOrProvince, l.address, l.region) from Location l where l.house.id = :houseId")
    Optional<LocationResponse> findLocationByHouseId(Long houseId);

    @Query("select new com.example.airbnbb7.dto.response.LocationResponse(l.id, l.townOrProvince, l.address, l.region) from Location l where l.id = :locationId")
    Optional<LocationResponse> findLocationById(Long locationId);

    @Query("select new com.example.airbnbb7.dto.response.LocationResponse(l.id,l.townOrProvince,l.address,l.region) from Location l where l = :location")
    LocationResponse convertToResponse(Location location);

}
