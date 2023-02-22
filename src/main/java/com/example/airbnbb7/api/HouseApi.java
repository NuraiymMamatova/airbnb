package com.example.airbnbb7.api;

import com.example.airbnbb7.db.customClass.SimpleResponse;
import com.example.airbnbb7.db.enums.HousesBooked;
import com.example.airbnbb7.db.enums.HousesStatus;
import com.example.airbnbb7.db.service.AnnouncementService;
import com.example.airbnbb7.db.service.HouseService;
import com.example.airbnbb7.dto.request.HouseRequest;
import com.example.airbnbb7.dto.response.AccommodationResponse;
import com.example.airbnbb7.dto.response.ApplicationResponse;
import com.example.airbnbb7.dto.response.ApplicationResponseForAdmin;
import com.example.airbnbb7.dto.response.HouseResponseSortedPagination;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
    @PreAuthorize("hasAuthority('USER')")
    public SimpleResponse saveHouse(@RequestBody HouseRequest houseRequest, Authentication authentication) {
        return houseService.save(houseRequest, authentication);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update House", description = "Update house by id")
    @PreAuthorize("hasAuthority('USER')")
    public SimpleResponse updateHouse(@PathVariable Long id,
                                      @RequestBody HouseRequest houseRequest, Authentication authentication) {
        return houseService.updateHouse(id, authentication, houseRequest);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete House", description = "Delete house by id")
    public SimpleResponse deleteHouseById(@PathVariable Long id, Authentication authentication) {
        return houseService.deleteByIdHouse(id, authentication);
    }

    @GetMapping("/pagination")
    @Operation(summary = "House get all pagination", description = "sort:High to low or Low t high" +
            "search:you can search by region address and townOrProvince" +
            "page:how many page do you want" +
            "size:how many houses were on one page" +
            "region:search by region" +
            "houseType:search by houseType")
    public ApplicationResponse findAllHousesPage(@RequestParam(name = "This is a global search field",required = false)String search,
                                                 @RequestParam(name = "Sorted by Regions",required = false) String region,
                                                 @RequestParam(name = "Sorted by Popular or The latest",required = false)String popularOrTheLatest,
                                                 @RequestParam(name = "Sorted by types of houses",required = false)String homeType,
                                                 @RequestParam(name = "Sorted by Price",required = false)String price,
                                                 @RequestParam(name = "Here you can write which page you want to open")Long page,
                                                 @RequestParam(name = "Here you can write how many objects should be on one page")Long pageSize) {
        return houseService.getAllPagination(search, region, popularOrTheLatest, homeType, price, page, pageSize);
    }

    @GetMapping("/popularAndLatest")
    @Operation(summary = "Get accommodations", description = "Popular and latest accommodations")
    public List<AccommodationResponse> getPopularLatestAccommodations(boolean popularHouse, boolean popularApartment) {
        return houseService.getLatestAccommodation(popularHouse, popularApartment);
    }

    @GetMapping("/announcement/{houseId}")
    @Operation(summary = "House inner page", description = "Any user can go through to view the house")
    public AnnouncementService announcementById(@PathVariable Long houseId, Authentication authentication) {
        return houseService.getAnnouncementById(houseId, authentication);
    }

    @PostMapping("changeStatusOfHouse/{houseId}")
    @Operation(summary = "Change status of house", description = "Only admin can change house status ")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse changeStatusOfHouse(@PathVariable Long houseId, @RequestParam(required = false) String message, @RequestParam("House status:" +
            " BLOCKED(for unblock also used this enum), " +
            " REJECT," +
            " ACCEPT,") HousesStatus housesStatus) {
        return houseService.changeStatusOfHouse(houseId, message, housesStatus);
    }

    @GetMapping("/announcementForAdmin")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Houses on Moderation", description = "Only admin can see these houses")
    public ApplicationResponseForAdmin getAllStatusOfTheWholeHouseOnModeration(@RequestParam("Which page do you want to open?") Long page,
                                                                               @RequestParam("How many houses do you want to see on one page?") Long pageSize) {
        return houseService.getAllStatusOfTheWholeHouseOnModeration(page, pageSize);
    }

    @GetMapping("/all_housing")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "All housing", description = "Only admin can see these houses")
    public List<HouseResponseSortedPagination> getAllHousing(@RequestParam(name = "Sorted by House Booked or Not booked", required = false) HousesBooked housesBooked,
                                                             @RequestParam(name = "Sorted by Popular or The latest",required = false) String popularOrTheLatest,
                                                             @RequestParam(name = "Sorted by types of houses",required = false) String houseType,
                                                             @RequestParam(name = "Sorted by Price",required = false) String price) {
        return houseService.getAllHousing(housesBooked, houseType, price, popularOrTheLatest);
    }

}
