package com.example.airbnbb7.db.service.serviceImpl;

import com.example.airbnbb7.db.entities.Feedback;
import com.example.airbnbb7.db.entities.House;
import com.example.airbnbb7.db.entities.Location;
import com.example.airbnbb7.db.enums.HouseType;
import com.example.airbnbb7.db.repository.FeedbackRepository;
import com.example.airbnbb7.db.repository.HouseRepository;
import com.example.airbnbb7.db.repository.LocationRepository;
import com.example.airbnbb7.db.service.HouseService;
import com.example.airbnbb7.dto.response.HouseResponseSortedPagination;
import com.example.airbnbb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService {

    private final HouseRepository houseRepository;

    private final LocationRepository locationRepository;

    private final FeedbackRepository feedbackRepository;

    @Override
    public List<HouseResponseSortedPagination> getAllPagination(HouseType houseType, String fieldToSort, String nameOfHouse, int page, int countOfHouses, String priceSort, String region) {
        Pageable pageable = PageRequest.of(page - 1, countOfHouses);
        String text;
        if (nameOfHouse == null)
            text = "";
        else text = nameOfHouse;
        List<House> houses = houseRepository.findAll();
        List<HouseResponseSortedPagination> houseResponses = houseRepository.pagination(text, pageable);
        List<HouseResponseSortedPagination> sortedHouseResponse = sort(pageable, houseType, region, priceSort, fieldToSort, houseResponses);
        for (HouseResponseSortedPagination response : sortedHouseResponse) {
            Long index = response.getId() - 1;
            if (index.equals(houses.get(Math.toIntExact(index)).getLocation().getId())) {
                response.setImages(houses.get(Math.toIntExact(index)).getImages());
                response.setLocationResponse(locationRepository.convertToResponse(houses.get(Math.toIntExact(index)).getLocation()));
                response.setHouseRating(getRating(response.getId()));
            } else {
                Location location = locationRepository.findById(response.getId()).orElseThrow(() -> new NotFoundException("location not found!"));
                response.setImages(houses.get(Math.toIntExact(index)).getImages());
                response.setLocationResponse(locationRepository.convertToResponse(location));
                response.setHouseRating(getRating(response.getId()));
            }
        }
        return sortedHouseResponse;
    }

    public List<HouseResponseSortedPagination> sort(Pageable pageable, HouseType houseType, String region, String priceSort, String fieldToSort, List<HouseResponseSortedPagination> sortedHouseResponse) {
        switch (fieldToSort) {
            case "homeType":
                return switch (houseType) {
                    case HOUSE -> houseRepository.getAllHouses(pageable);
                    case APARTMENT -> houseRepository.getAllApartments(pageable);
                };
            case "homePrice":
                if (priceSort.equals("High to low")) {
                    sortedHouseResponse.sort(Comparator.comparing(HouseResponseSortedPagination::getPrice).reversed());
                } else if (priceSort.equals("Low to high")) {
                    sortedHouseResponse.sort(Comparator.comparing(HouseResponseSortedPagination::getPrice));
                }
                break;
            case "region":
                switch (region) {
                    case "Bishkek":
                        return houseRepository.regionHouses("Bishkek", pageable);
                    case "Osh":
                        return houseRepository.regionHouses("Osh", pageable);
                    case "Batken":
                        return houseRepository.regionHouses("Batken", pageable);
                    case "Talas":
                        return houseRepository.regionHouses("Talas", pageable);
                    case "Naryn":
                        return houseRepository.regionHouses("Naryn", pageable);
                    case "Chui":
                        return houseRepository.regionHouses("Chui", pageable);
                    case "Issyk-Kul":
                        return houseRepository.regionHouses("Issyk-Kul", pageable);
                    case "Jalal-Abat":
                        return houseRepository.regionHouses("Jalal-Abat", pageable);
                }
        }
        return sortedHouseResponse;
    }

    public double getRating(Long houseId) {
        List<Feedback> feedbacks = feedbackRepository.getAllFeedbackByHouseId(houseId);
        List<Integer> ratings = new ArrayList<>();
        for (Feedback feedback : feedbacks) {
            ratings.add(feedback.getRating());
        }
        double sum = 0;
        for (Integer rating : ratings) {
            sum += rating;
        }
        sum = sum / ratings.size();
        String.format("%.1f", sum);
        return sum;
    }
}
