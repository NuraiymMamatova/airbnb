package com.example.airbnbb7.db.service.serviceImpl;

import com.example.airbnbb7.db.entities.House;
import com.example.airbnbb7.db.enums.HouseType;
import com.example.airbnbb7.db.repository.HouseRepository;
import com.example.airbnbb7.db.repository.LocationRepository;
import com.example.airbnbb7.dto.response.HouseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HouseServiceImpl {

    private final HouseRepository houseRepository;
    private final LocationRepository locationRepository;

    public List<HouseResponse> getAll(HouseType houseType, String fieldToSort, String nameOfHouse, int page, int countOfHouses, String priceSort, String region) {
        Pageable pageable = PageRequest.of(page - 1, countOfHouses);
        String text;
        if (nameOfHouse == null)
            text = "";
        else text = nameOfHouse;
        List<House> houses = houseRepository.findAll();
        List<HouseResponse> houseResponses = houseRepository.pagination(text, pageable);
        List<HouseResponse> sortedHouseResponse = sort(houseType, region, priceSort, fieldToSort, houseResponses);
        for (HouseResponse response : sortedHouseResponse) {
            Long index1 = response.getId();
            response.setImages(houses.get(Math.toIntExact(index1 - 1)).getImages());
            response.setLocationResponse(locationRepository.convertToResponse(houses.get(Math.toIntExact(index1 - 1)).getLocation()));
        }
        return sortedHouseResponse;
    }

    public List<HouseResponse> sort(HouseType houseType, String region, String priceSort, String fieldToSort, List<HouseResponse> sortedHouseResponse) {
        switch (fieldToSort) {
            case "homeType":
                switch (houseType) {
                    case HOUSE:
                        return houseRepository.getAllHouses();
                    case APARTMENT:
                        return houseRepository.getAllApartments();
                }
            case "homePrice":
                if (priceSort.equals("High to low")) {
                    sortedHouseResponse.sort(Comparator.comparing(HouseResponse::getPrice).reversed());
                } else if (priceSort.equals("Low to high")) {
                    sortedHouseResponse.sort(Comparator.comparing(HouseResponse::getPrice));
                }
                break;
            case "region":
                switch (region) {
                    case "Bishkek":
                        return houseRepository.regionHouses("Bishkek");
                    case "Osh":
                        return houseRepository.regionHouses("Osh");
                    case "Batken":
                        return houseRepository.regionHouses("Batken");
                    case "Talas":
                        return houseRepository.regionHouses("Talas");
                    case "Naryn":
                        return houseRepository.regionHouses("Naryn");
                    case "Chui":
                        return houseRepository.regionHouses("Chui");
                    case "Issyk-Kul":
                        return houseRepository.regionHouses("Issyk-Kul");
                    case "Jalal-Abat":
                        return houseRepository.regionHouses("Jalal-Abat");
                }
        }
        return sortedHouseResponse;
    }
}
