package com.example.airbnbb7.api;

import com.example.airbnbb7.db.service.UserService;
import com.example.airbnbb7.dto.response.ProfileResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "User Api", description = "User Api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserApi {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "User Profile", description = "With this method, you can see your houses and booked houses. Houses that are already being checked by the administrator.")
    public ProfileResponse userProfile(@RequestParam(name = "Select one of these Lists Bookings, My announcement, On moderation", required = false) String mainInUserProfile,
                                       @RequestParam(name = "Here you can sort as you wish(In wish list)", required = false) String sortHousesAsDesired,
                                       @RequestParam(name = "Here you can sort the apartment(Apartment)", required = false) String sortHousesByApartments,
                                       @RequestParam(name = "Here you can sort the house(House)", required = false) String sortHousesByHouses,
                                       @RequestParam(name = "Here you can sort by price", required = false) String sortingHousesByValue,
                                       @RequestParam(name = "Here you can sort by rating", required = false) String sortingHousesByRating,
                                       @RequestParam(name = "Here you can write which page you want to open") int page,
                                       @RequestParam(name = "Here you can write how many objects should be on one page") int size,
                                       Authentication authentication) {
        return userService.userProfile(mainInUserProfile, sortHousesByApartments, sortHousesByHouses, sortHousesAsDesired, sortingHousesByValue, sortingHousesByRating, authentication, page, size);
    }
}
