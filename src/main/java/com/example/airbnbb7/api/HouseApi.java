package com.example.airbnbb7.api;

import com.example.airbnbb7.db.enums.HouseType;
import com.example.airbnbb7.db.repository.HouseRepository;
import com.example.airbnbb7.db.service.serviceImpl.HouseServiceImpl;
import com.example.airbnbb7.dto.response.HouseResponse;
import com.example.airbnbb7.db.service.HouseService;
import com.example.airbnbb7.dto.request.HouseRequest;
import com.example.airbnbb7.dto.response.HouseResponse;
import com.example.airbnbb7.dto.response.HouseResponseSortedPagination;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/houses")
@Tag(name = "House Api", description = "House Api")
public class HouseApi {

    private final HouseService houseService;

    @PostMapping
    @Operation(summary = "Save house", description = "Save house and location")
    public HouseResponse saveHouse(@RequestBody HouseRequest houseRequest) {
        return houseService.save(houseRequest);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update House", description = "Update house by id")
    public HouseResponse updateHouse(@PathVariable Long id,
                                     @RequestBody HouseRequest houseRequest) {
        return houseService.updateHouse(id, houseRequest);

    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete House", description = "Delete house by id")
    public HouseResponse deleteHouseById(@PathVariable Long id) {
        return houseService.deleteByIdHouse(id);
    }

    @GetMapping("/pagination")
    @Operation(summary = "House get all pagination", description = "This is get all pagination for houses")
    public List<HouseResponseSortedPagination> findAllHousesPage(@RequestParam(name = "sortOrFilter", required = false) String fieldToSort,
                                                                 @RequestParam(name = "text", required = false) String text,
                                                                 @RequestParam int page,
                                                                 @RequestParam int size,
                                                                 @RequestParam(name = "priceSort", required = false) String priceSort,
                                                                 @RequestParam(name = "region", required = false) String region,
                                                                 @RequestParam(name = "houseType", required = false) HouseType houseType) {
        return houseService.getAllPagination(houseType, fieldToSort, text, page, size, priceSort, region);
    }

    @GetMapping("/search")
    @Operation(summary = "Global search", description = "Global Home Search")
    public List<HouseResponse> search(@RequestParam("search") String search) {
        return houseService.globalSearch(search);
    }
}
