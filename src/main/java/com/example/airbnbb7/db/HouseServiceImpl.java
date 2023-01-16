package com.example.airbnbb7.db;

import com.example.airbnbb7.db.entities.House;
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
    private final HouseResponseConverter houseResponseConverter;
    private final HouseRequestConverter houseRequestConverter;
    
    public HouseResponseConverter getAll(String text, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        HouseResponseConverter instructorConverterResponse = new HouseResponseConverter();
        instructorConverterResponse.setHouseResponseList(viewPagination(search(text, pageable)));
        return instructorConverterResponse;
    }

    public List<HouseResponse> viewPagination(List<House> houses) {
        List<HouseResponse> houseResponses = new ArrayList<>();
        for (House house : houses) {
            houseResponses.add(houseResponseConverter.create(house));
        }
        return houseResponses;
    }

    public List<House> search(String name, Pageable pageable) {
        String text = name == null ? "" : name;
        return houseRepository.searchPagination(text.toUpperCase(), pageable);
    }


}
