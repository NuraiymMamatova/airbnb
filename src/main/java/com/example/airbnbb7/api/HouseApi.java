package com.example.airbnbb7.api;

import com.example.airbnbb7.db.service.HouseService;
import com.example.airbnbb7.db.service.LocationService;
import com.example.airbnbb7.dto.request.HouseRequest;
import com.example.airbnbb7.dto.response.HouseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/houses")
@Tag(name = "HouseApi", description = "house controller")
public class HouseApi {

    private final HouseService houseService;
    private final LocationService locationService;

    @PostMapping
    public void saveHouse(@RequestBody HouseRequest houseRequest) {
        houseService.save(houseRequest);

    }

    @DeleteMapping("/delete/{id}")
//    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public HouseResponse deleteHouse(@PathVariable Long id) {
        return houseService.deleteByIdHouse(id);
    }

    @PutMapping("/update/{id}")
//    @PreAuthorize("hasRole('USER')")
    public HouseResponse update(@PathVariable Long id,
                                @RequestBody HouseRequest houseRequest) {
        return houseService.updateHouse(id, houseRequest);
    }

}
