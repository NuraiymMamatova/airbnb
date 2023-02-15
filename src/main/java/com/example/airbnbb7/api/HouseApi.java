package com.example.airbnbb7.api;

import com.example.airbnbb7.db.customClass.SimpleResponse;
import com.example.airbnbb7.db.enums.HouseType;
import com.example.airbnbb7.db.service.AnnouncementService;
import com.example.airbnbb7.db.service.HouseService;
import com.example.airbnbb7.dto.request.HouseRequest;
import com.example.airbnbb7.dto.response.ApplicationResponse;
import com.example.airbnbb7.dto.response.HouseResponse;
import com.example.airbnbb7.dto.response.HouseResponseSortedPagination;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/houses")
@Tag(name = "House Api", description = "House Api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class HouseApi {

    private final HouseService houseService;

    @PostMapping
    @Operation(summary = "Save house", description = "Save house and location")
    public SimpleResponse saveHouse(@RequestBody HouseRequest houseRequest) {
        return houseService.save(houseRequest);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update House", description = "Update house by id")
    public SimpleResponse updateHouse(@PathVariable Long id,
                                      @RequestBody HouseRequest houseRequest) {
        return houseService.updateHouse(id, houseRequest);

    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete House", description = "Delete house by id")
    public SimpleResponse deleteHouseById(@PathVariable Long id) {
        return houseService.deleteByIdHouse(id);
    }

    @GetMapping("/pagination")
    @Operation(summary = "House get all pagination", description = "sort:High to low or Low t high" +
            "search:you can search by region address and townOrProvince" +
            "page:how many page do you want" +
            "size:how many houses were on one page" +
            "region:search by region" +
            "houseType:search by houseType")
    public ApplicationResponse findAllHousesPage(@RequestParam(name = "sort", required = false) String sort,
                                                 @RequestParam(name = "search", required = false) String search,
                                                 @RequestParam int page,
                                                 @RequestParam int size,
                                                 @RequestParam(name = "region", required = false) String region,
                                                 @RequestParam(name = "houseType", required = false) HouseType houseType,
                                                 @RequestParam(name = "popularOrLatest", required = false) String popularAndLatest) {
        return houseService.getAllPagination(houseType, sort, search, page, size, region, popularAndLatest);
    }

    @GetMapping("/announcement/{houseId}")
    @Operation(summary = "House inner page", description = "Any user can go through to view the house")
    public AnnouncementService announcementById(@PathVariable Long houseId) {
        return houseService.getAnnouncementById(houseId);
    }

    @GetMapping("/search")
    @Operation(summary = "Global search", description = "Global Home Search")
    public List<HouseResponse> search(@RequestParam("search") String search) {
        return houseService.globalSearch(search);
    }

    @GetMapping("/searchNearby")
    @Operation(summary = "Houses search nearby", description = "Any user can go through to view the houses")
    public List<HouseResponseSortedPagination> searchNearby(@RequestParam double userLatitude, @RequestParam double userLongitude) throws IOException {
        return houseService.searchNearby(userLatitude, userLongitude);
    }
}
