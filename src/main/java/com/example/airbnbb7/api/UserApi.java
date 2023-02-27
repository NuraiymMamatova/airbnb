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
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/users")
@Tag(name = "User Api", description = "User Api")
public class UserApi {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "User Profile", description = """
            With this method, you can see your houses and booked houses. Houses that are already being checked by the administrator.
            1 Select one of these Lists Bookings, My announcement, On moderation
            2 Here you can sort as you wish(In wish list)
            3 Here you can sort the apartment(Apartment)
            4 Here you can sort the house(House)
            5 Here you can sort by price
            6 Here you can sort by rating
            7 Here you can write which page you want to open
            8 Here you can write how many objects should be on one page
            """)
    public ProfileResponse userProfile(@RequestParam(name = "1", required = false) String mainInUserProfile,
                                       @RequestParam(name = "2", required = false) String sortHousesAsDesired,
                                       @RequestParam(name = "3", required = false) String sortHousesByApartments,
                                       @RequestParam(name = "4", required = false) String sortHousesByHouses,
                                       @RequestParam(name = "5", required = false) String sortingHousesByValue,
                                       @RequestParam(name = "6", required = false) String sortingHousesByRating,
                                       @RequestParam(name = "7") int page,
                                       @RequestParam(name = "8") int size,
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
