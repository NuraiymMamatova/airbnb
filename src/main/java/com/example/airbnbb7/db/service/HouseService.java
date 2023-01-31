package com.example.airbnbb7.db.service;

<<<<<<< HEAD
import com.example.airbnbb7.dto.response.AccommodationResponse;
import org.springframework.stereotype.Service;

@Service
public interface HouseService {
    AccommodationResponse getLatestAccommodation ();
=======
import com.example.airbnbb7.db.enums.HouseType;
import com.example.airbnbb7.dto.response.HouseResponseSortedPagination;

import java.util.List;

public interface HouseService {

    List<HouseResponseSortedPagination> getAllPagination(HouseType houseType, String fieldToSort, String nameOfHouse, int page, int countOfHouses, String priceSort, String region);

>>>>>>> f80b5ac2e15977e6d61b619bfb03be351ac8318b
}
