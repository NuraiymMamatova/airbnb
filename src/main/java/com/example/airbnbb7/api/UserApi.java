package com.example.airbnbb7.api;

import com.example.airbnbb7.db.service.UserService;
import com.example.airbnbb7.dto.response.ProfileResponse;
import com.example.airbnbb7.dto.response.UserAdminResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Get all users", description = "Get all users from database")
    public List<UserAdminResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Delete users", description = "Delete users by id from database")
    public List<UserAdminResponse> deleteUser(@PathVariable("id") Long id) {
        return userService.deleteUser(id);
    }
}
