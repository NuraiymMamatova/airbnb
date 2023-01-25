package com.example.airbnbb7.db.service.serviceImpl;

import com.example.airbnbb7.db.entities.House;
import com.example.airbnbb7.db.entities.Location;
import com.example.airbnbb7.db.enums.HouseType;
import com.example.airbnbb7.db.repository.HouseRepository;
import com.example.airbnbb7.db.repository.LocationRepository;
import com.example.airbnbb7.dto.response.HouseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class HouseServiceImpl {

    private final HouseRepository houseRepository;
    private final LocationRepository locationRepository;

    public List<HouseResponse> getAll(HouseType houseType, String fieldToSort, String nameOfHouse, int page, int countOfHouses, String priceSort, String region) {
        Pageable pageable = PageRequest.of(page - 1, countOfHouses);
//        List<HouseResponse> houseResponses = new ArrayList<>();
//        List<House> houses = search(text, pageable);si

        String text;
        if (nameOfHouse == null)
            text = "";
        else text = nameOfHouse;
        List<House> houses = houseRepository.findAll();
        List<HouseResponse> houseResponses = houseRepository.pagination(text, pageable);
        houseResponses = sort(houseType, region, priceSort, fieldToSort, houseResponses);
        int index = 0;
        for (HouseResponse response : houseResponses) {
            response.setImages(houses.get(index).getImages());
            response.setLocationResponse(locationRepository.convertToResponseById(houses.get(index).getLocation() ));
            index++;
        }
//        System.out.println(houseResponses.toString());
        return houseResponses;
    }


    public List<HouseResponse> sort(HouseType houseType, String region, String priceSort, String fieldToSort, List<HouseResponse> sortedHouseResponse) {
        switch (fieldToSort) {
            case "homeType":
                switch (houseType) {
                    case HOUSE:
                        System.out.println(getAllHouses());
                    case APARTMENT:
                        System.out.println(getAllApartment());
                        break;
                }
            case "homePrice":
                if (priceSort.equals("High to low")) {
                    sortedHouseResponse.sort(Comparator.comparing(HouseResponse::getPrice).reversed());
                } else if (priceSort.equals("Low to high")) {
                    sortedHouseResponse.sort(Comparator.comparing(HouseResponse::getPrice));
                }
                break;
            case "region":
                switch (region){
                    case "Bishkek":
                        houseRepository.regionHouses("Bishkek");
                    case "Osh":
                        houseRepository.regionHouses("Osh");
                    case "Batken":
                        houseRepository.regionHouses("Batken");
                    case "Talas":
                        houseRepository.regionHouses("Talas");
                    case "Naryn":
                        houseRepository.regionHouses("Naryn");
                    case "Chui":
                        houseRepository.regionHouses("Chui");
                    case "Issyk-Kul":
                        houseRepository.regionHouses("Issyk-Kul");
                    case "Jalal-Abat":
                        System.out.println(houseRepository.regionHouses("Jalal-Abat"));
                }
        }
        return sortedHouseResponse;
    }

    public List<HouseResponse> getAllHouses( ) {
        List<HouseResponse> houseResponses = houseRepository.getAllHouses();
        return houseResponses;
    }

    public List<HouseResponse> getAllApartment( ) {
        List<HouseResponse> houseResponses = houseRepository.getAllApartments();
        return houseResponses;
    }
}
