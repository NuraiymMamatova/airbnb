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
    public ProfileResponse userProfile(@RequestParam(name = "mainInUserProfile", required = false) String mainInUserProfile,
                                       @RequestParam(name = "sortHousesAsDesired", required = false) String sortHousesAsDesired,
                                       @RequestParam(name = "sortHousesByApartments", required = false) String sortHousesByApartments,
                                       @RequestParam(name = "sortHousesByHouses", required = false) String sortHousesByHouses,
                                       @RequestParam(name = "sortingHousesByValue", required = false) String sortingHousesByValue,
                                       @RequestParam(name = "sortingHousesByRating", required = false) String sortingHousesByRating,
                                       @RequestParam(name = "page") int page,
                                       @RequestParam(name = "size") int size,
                                       Authentication authentication) {
        return userService.userProfile(mainInUserProfile, sortHousesByApartments, sortHousesByHouses, sortHousesAsDesired, sortingHousesByValue, sortingHousesByRating, authentication, page, size);
    }
}
