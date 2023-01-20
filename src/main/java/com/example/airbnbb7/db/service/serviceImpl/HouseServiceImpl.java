package com.example.airbnbb7.db.service.serviceImpl;

import com.example.airbnbb7.db.entities.House;
import com.example.airbnbb7.db.entities.Location;
import com.example.airbnbb7.db.repository.HouseRepository;
import com.example.airbnbb7.dto.response.HouseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HouseServiceImpl {

    private final HouseRepository houseRepository;

    public List<HouseResponse> getAll(String text, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        List<HouseResponse> houseResponses = new ArrayList<>();
        List<House> houses = search(text, pageable);
        int count = 0;
        for (House house : houses) {
            HouseResponse houseResponse = houseRepository.convertToResponseById(house.getId());
            houseResponse.setLocation(houses.get(count).getLocation());
            houseResponses.add(houseResponse);
            houseResponses.get(count).setImages(house.getImages());
            count++;
        }
        return houseResponses;
    }

    public List<House> search(String name, Pageable pageable) {
        String text;
        if (name == null)
            text = "";
        else text = name;
        return houseRepository.searchPagination(text.toUpperCase(), pageable);
    }
}
