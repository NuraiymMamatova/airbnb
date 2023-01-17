package com.example.airbnbb7.db.service.serviceImpl;

import com.example.airbnbb7.converter.response.HouseResponseConverter;
import com.example.airbnbb7.db.entities.House;
import com.example.airbnbb7.db.repository.HouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HouseServiceImpl {

    private final HouseRepository houseRepository;
    private final HouseResponseConverter houseResponseConverter;

    public HouseResponseConverter getAll(String text, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        HouseResponseConverter instructorConverterResponse = new HouseResponseConverter();
        instructorConverterResponse.setHouseResponseList(houseResponseConverter.getAll(search(text, pageable)));
        return instructorConverterResponse;
    }

    public List<House> search(String name, Pageable pageable) {
        String text = name == null ? "" : name;
        return houseRepository.searchPagination(text.toUpperCase(), pageable);
    }
}
