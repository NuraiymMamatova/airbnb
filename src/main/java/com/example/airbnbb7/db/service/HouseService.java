package com.example.airbnbb7.db.service;

import com.example.airbnbb7.db.enums.HouseType;
import com.example.airbnbb7.dto.response.HouseResponseSortedPagination;
import com.example.airbnbb7.dto.response.HouseResponse;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HouseService {

    List<HouseResponseSortedPagination> getAllPagination(HouseType houseType, String fieldToSort, String nameOfHouse, int page, int countOfHouses, String priceSort, String region);

    List<HouseResponse> globalSearch(String searchEngine);
}
