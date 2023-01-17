package com.example.airbnbb7.api;

import com.example.airbnbb7.db.service.HouseService;
import com.example.airbnbb7.dto.request.HouseRequest;
import com.example.airbnbb7.dto.response.HouseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/house/api")
public class HouseApi {
    private final HouseService houseService;

    @PostMapping("/save/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public HouseResponse save(@RequestBody HouseRequest houseRequest, @PathVariable Long id) {

        return houseService.saveHouse(houseRequest, id);
    }

    @PostMapping("/acceptHouse/{id}")
    @PreAuthorize("isAuthenticated()")
    public HouseResponse acceptHouse(@PathVariable Long id) {
        return houseService.acceptHouseById(id);
    }





}
