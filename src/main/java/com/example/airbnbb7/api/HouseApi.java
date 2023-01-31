package com.example.airbnbb7.api;

import com.example.airbnbb7.db.enums.HouseType;
import com.example.airbnbb7.db.service.AnnouncementService;
import com.example.airbnbb7.db.service.serviceImpl.HouseServiceImpl;
import com.example.airbnbb7.dto.response.HouseResponseSortedPagination;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/houses")
@Tag(name = "House Api", description = "House Api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class HouseApi {

    private final HouseServiceImpl houseService;

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

    @GetMapping("/announcements/{houseId}/{userId}")
    @Operation(summary = "House inner page", description = "Any user can go through to view the house")
    public AnnouncementService announcementsForVendor(@PathVariable Long houseId, @PathVariable Long userId) {
        return houseService.getHouse(houseId, userId);
    }

}
