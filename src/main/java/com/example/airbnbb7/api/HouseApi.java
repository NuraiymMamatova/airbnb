package com.example.airbnbb7.api;

import com.example.airbnbb7.converter.response.HouseResponseConverter;
import com.example.airbnbb7.db.service.serviceImpl.HouseServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/houses")
public class HouseApi {

    private final HouseServiceImpl houseService;


    @GetMapping
    public HouseResponseConverter findAllInstructors(@RequestParam(name = "text", required = false) String text,
                                                     @RequestParam int page,
                                                     @RequestParam int size) {
        return houseService.getAll(text, page, size);
    }
}
