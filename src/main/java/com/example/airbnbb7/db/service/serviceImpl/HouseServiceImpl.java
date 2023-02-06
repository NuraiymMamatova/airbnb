package com.example.airbnbb7.db.service.serviceImpl;

import com.example.airbnbb7.db.entities.Feedback;
import com.example.airbnbb7.db.entities.House;
import com.example.airbnbb7.db.entities.Location;
import com.example.airbnbb7.db.enums.HouseType;
import com.example.airbnbb7.db.repository.FeedbackRepository;
import com.example.airbnbb7.db.repository.HouseRepository;
import com.example.airbnbb7.db.repository.LocationRepository;
import com.example.airbnbb7.db.service.HouseService;
import com.example.airbnbb7.dto.response.AccommodationResponse;
import com.example.airbnbb7.dto.response.HouseResponse;
import com.example.airbnbb7.dto.response.HouseResponseSortedPagination;
import com.example.airbnbb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.apache.catalina.security.SecurityUtil.remove;

@Service
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService {

    private final HouseRepository houseRepository;

    private final LocationRepository locationRepository;

    private final FeedbackRepository feedbackRepository;

    @Override
    public List<HouseResponse> getPopularHouses() {
        List<HouseResponse> houseResponses = houseRepository.getPopularHouse();
        List<HouseResponse> h = new ArrayList<>();
        double rating = 0;
        Long booked = 0L;
        for (HouseResponse houseResponse : houseResponses) {
            double r = getRating(houseResponse.getId());
            if (r > rating && houseResponse.getCountOfBookedUser() > booked) {
                rating = houseResponse.getRating();
                booked = houseResponse.getCountOfBookedUser();
            }
            houseResponse.setRating(getRating(houseResponse.getId()));
            h.add(houseResponse);
            remove(houseResponse);
        }
        return h.stream().limit(3).toList();
    }

    @Override
    public HouseResponse getPopularApartment() {
        System.out.println("ser1");
        List<HouseResponse> houseResponses = houseRepository.getPopularApartment();
        System.out.println("ser");
        List<HouseResponse> h = new ArrayList<>();
        System.out.println("ser3");
        double rating = 0;
        int booked = 0;
        for (HouseResponse houseResponse : houseResponses) {
            double r = getRating(houseResponse.getId());
            if (r > rating && (houseResponse.getCountOfBookedUser() > booked)) {
                rating = houseResponse.getRating();
                booked = Math.toIntExact(houseResponse.getCountOfBookedUser());
            }
            h.add(houseResponse);
            houseResponses.remove(houseResponse);
        }
        /**
         if (value != null) {
         //Do something with value
         } else {
         //Ignore null value
         }

         //Ignore null values
         if (myObject != null) {
         // Do something
         }
         */
        System.out.println("ser10");
        return h.stream().limit(1).findFirst().orElseThrow(() -> new NotFoundException("Apartment not found!"));
    }

    @Override
    public HouseResponse getLatestAccommodation() {
        HouseResponse houseResponse = houseRepository.getLatestAccommodation().stream().findFirst().get();
        House house = houseRepository.findById(houseResponse.getId()).get();
        houseResponse.setLocationResponse(locationRepository.convertToResponse(house.getLocation()));
        houseResponse.setImages(house.getImages());
        return houseResponse;
    }

    @Override
    public List<HouseResponseSortedPagination> getAllPagination(HouseType houseType, String fieldToSort, String
            nameOfHouse, int page, int countOfHouses, String priceSort, String region) {
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

    @Override
    public List<HouseResponseSortedPagination> sort(Pageable pageable, HouseType houseType, String region, String
            priceSort, String fieldToSort, List<HouseResponseSortedPagination> sortedHouseResponse) {
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

    @Override
    public double getRating(Long houseId) {
        List<Feedback> feedbacks = feedbackRepository.getAllFeedbackByHouseId(houseId);
        List<Integer> ratings = new ArrayList<>();

        for (Feedback feedback : feedbacks) {
            ratings.add(feedback.getRating());
        }
        double sum = 0;
        for (Integer rating : ratings) {
            sum += rating;
            sum += rating;
        }
        sum = sum / ratings.size();
        String.format("%.1f", sum);
        return sum;
    }

}