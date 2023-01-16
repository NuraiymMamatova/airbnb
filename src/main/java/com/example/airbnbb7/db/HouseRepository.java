package com.example.airbnbb7.db;

import com.example.airbnbb7.db.entities.House;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseRepository extends JpaRepository<House,Long> {

    @Query("select i from House i where upper(i.title) like concat('%',:pagination, '%')" +
            " or upper(i.title) like concat('%',:pagination, '%' ) ")
    List<House> searchPagination(@Param("pagination") String pagination, Pageable pageable);

}
