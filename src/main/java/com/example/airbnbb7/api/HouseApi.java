package com.example.airbnbb7.api;

import com.example.airbnbb7.db.service.HouseService;
import com.example.airbnbb7.dto.request.HouseRequest;
import com.example.airbnbb7.dto.response.HouseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/house/api")
public class HouseApi {
    private final HouseService houseService;

    @PostMapping("/save/{id}")
    public HouseResponse save(@PathVariable Long id, @RequestBody HouseRequest houseRequest) {
        return houseService.saveHouse(id, houseRequest);
    }

    @DeleteMapping("/delete/{id}")
    public HouseResponse deleteHouse(@PathVariable Long id) {
        return houseService.deleteByIdHouse(id);
    }

    @PutMapping("/update/{id}")
    public HouseResponse update(@PathVariable Long id,
                                @RequestBody HouseRequest houseRequest) {
        return houseService.updateHouse(id, houseRequest);
    }


}
