package com.example.airbnbb7.api;

import com.example.airbnbb7.db.customClass.SimpleResponse;
import com.example.airbnbb7.db.enums.HousesStatus;
import com.example.airbnbb7.db.service.HouseService;
import com.example.airbnbb7.db.service.MasterInterface;
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

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/houses")
@Tag(name = "House Api", description = "House Api")
public class HouseApi {

    private final HouseService houseService;

    @PostMapping
    @Operation(summary = "Save house", description = "Save house and location")
    @PreAuthorize("hasAuthority('USER')")
    public SimpleResponse saveHouse(@RequestBody HouseRequest houseRequest, Authentication authentication) throws IOException {
        return houseService.save(houseRequest, authentication);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update House", description = "Update house by id")
    @PreAuthorize("hasAuthority('USER')")
    public SimpleResponse updateHouse(@PathVariable Long id,
                                      @RequestBody HouseRequest houseRequest, Authentication authentication) throws IOException {
        return houseService.updateHouse(id, authentication, houseRequest);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete House", description = "Delete house by id")
    public SimpleResponse deleteHouseById(@PathVariable Long id, Authentication authentication) {
        return houseService.deleteByIdHouse(id, authentication);
    }

    @CrossOrigin
    @GetMapping("/pagination")
    @Operation(summary = "House get all pagination", description =
            "search: you can search by region address and townOrProvince" +
                    "region: search by region" +
                    "houseType: search by houseType" +
                    "price: High to low or Low t high" +
                    "page: how many page do you want" +
                    "size: how many houses were on one page")
    public ApplicationResponse findAllHousesPage(@RequestParam(required = false) String search,
                                                 @RequestParam(required = false) String region,
                                                 @RequestParam(required = false) String popularOrTheLatest,
                                                 @RequestParam(required = false) String homeType,
                                                 @RequestParam(required = false) String price,
                                                 @RequestParam Long page,
                                                 @RequestParam Long pageSize,
                                                 @RequestParam(required = false, defaultValue = "0") double userLatitude,
                                                 @RequestParam(required = false, defaultValue = "0") double userLongitude) throws IOException {
        return houseService.getAllPagination(search, region, popularOrTheLatest, homeType, price, page, pageSize, userLatitude, userLongitude);
    }

    @GetMapping("/popularAndLatest")
    @Operation(summary = "Get accommodations", description = "Popular and latest accommodations")
    public List<AccommodationResponse> getPopularLatestAccommodations(boolean popularHouse, boolean popularApartment) {
        return houseService.getLatestAccommodation(popularHouse, popularApartment);
    }

    @GetMapping("/announcement/{houseId}")
    @Operation(summary = "House inner page", description = "Any user can go through to view the house")
    public MasterInterface announcementById(@PathVariable Long houseId, Authentication authentication) {
        return houseService.getAnnouncementById(houseId, authentication);
    }

    @PostMapping("/changeStatusOfHouse/{houseId}")
    @Operation(summary = "Change status of house", description = """
            Only admin can change house status
            House status:
            #BLOCKED(for unblock also used this enum)
            #REJECT
            #ACCEPT""")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse changeStatusOfHouse(@PathVariable Long houseId, @RequestParam(required = false) String message, @RequestParam() HousesStatus housesStatus) {
        return houseService.changeStatusOfHouse(houseId, message, housesStatus);
    }

    @GetMapping("/announcementForAdmin")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Houses on Moderation", description = "Only admin can see these houses" +
            "page: Which page do you want to open?" +
            "pageSize: How many houses do you want to see on one page?")
    public ApplicationResponseForAdmin getAllStatusOfTheWholeHouseOnModeration(@RequestParam Long page,
                                                                               @RequestParam Long pageSize) {
        return houseService.getAllStatusOfTheWholeHouseOnModeration(page, pageSize);
    }

    @GetMapping("/allHousing")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "All housing", description = """
            Only admin can see these houses:
            1) housesBooked = 'Booked' or 'Not booked'
            2) popularOrTheLatest = 'Popular' or 'The latest'
            3) houseType = 'Apartment' or 'House'
            4) price = 'High to low' or 'Low to high'""")
    public List<HouseResponseSortedPagination> getAllHousing(@RequestParam(required = false) String housesBooked,
                                                             @RequestParam(required = false) String popularOrTheLatest,
                                                             @RequestParam(required = false) String houseType,
                                                             @RequestParam(required = false) String price) throws IOException {
        return houseService.getAllHousing(housesBooked, houseType, price, popularOrTheLatest);
    }

    @GetMapping("/searchNearby")
    @Operation(summary = "Houses search nearby", description = "Any user can go through to view the houses")
    public List<HouseResponseSortedPagination> searchNearby(@RequestParam double userLatitude, @RequestParam double userLongitude) throws IOException {
        return houseService.searchNearby(userLatitude, userLongitude);
    }

    @DeleteMapping("/delete_image/{imageId}")
    @Operation(summary = "Delete image by id.", description = "Only owner can delete image")
    public SimpleResponse deleteImage(@PathVariable Long imageId, Authentication authentication) {
        return houseService.deleteImageById(imageId, authentication);
    }

}