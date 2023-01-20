package com.example.airbnbb7.api;

import com.example.airbnbb7.db.service.serviceImpl.HouseServiceImpl;
import com.example.airbnbb7.dto.response.HouseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/houses")
public class HouseApi {

    private final HouseServiceImpl houseService;


    @GetMapping
    public List<HouseResponse> findAllHousesPage(@RequestParam(name = "text", required = false) String text,
                                                @RequestParam int page,
                                                @RequestParam int size) {
        return houseService.getAll(text, page, size);
    }
}
