package com.example.airbnbb7.api;

import com.example.airbnbb7.db.customClass.SimpleResponse;
import com.example.airbnbb7.db.service.MasterInterface;
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
@CrossOrigin(origins = "*")
@RequestMapping("/api/users")
@Tag(name = "User Api", description = "User Api")
public class UserApi {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "User Profile", description = """
            With this method, you can see your houses and booked houses. Houses on moderation. 
            1) mainInUserProfile = Select one from this list Bookings, My announcement, On moderation 
            2) sortHousesAsDesired = Here you can sort as you wish (In wish list) 
            3) sortHousesByApartments = Here you can sort the apartments (Apartment) 
            4) sortHousesByHouses = Here you can sort the houses (House) 
            5) sortingHousesByValue = Here you can sort by price 
            6) sortingHousesByRating = Here you can sort by rating 
            7) page = Here you can write which page you want to open 
            8) size = Here you can write how many objects should be on one page 
            """)
    public ProfileResponse userProfile(@RequestParam(required = false) String mainInUserProfile,
                                       @RequestParam(required = false) String sortHousesAsDesired,
                                       @RequestParam(required = false) String sortHousesByApartments,
                                       @RequestParam(required = false) String sortHousesByHouses,
                                       @RequestParam(required = false) String sortingHousesByValue,
                                       @RequestParam(required = false) String sortingHousesByRating,
                                       @RequestParam int page,
                                       @RequestParam int size,
                                       Authentication authentication) {
        return userService.userProfile(mainInUserProfile, sortHousesAsDesired, sortHousesByApartments, sortHousesByHouses, sortingHousesByValue, sortingHousesByRating, authentication, page, size);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Get all users", description = "Get all users from database")
    public List<UserAdminResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Delete users", description = "Delete users by id from database")
    public SimpleResponse deleteUser(@PathVariable("userId") Long userId) {
        return userService.deleteUser(userId);
    }

    @GetMapping("/profile")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Get user by id ", description = "User profile for administrator" +
            ", bookingsOrAnnouncement = 'Bookings' or 'My announcement'")
    public MasterInterface getUserById(@RequestParam Long userId,
                                       @RequestParam(required = false) String bookingsOrAnnouncement) {
        return userService.getUserById(userId, bookingsOrAnnouncement);
    }

    @PostMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "All blocked", description = "This method blocks all user's homes")
    public SimpleResponse allBlocked(@PathVariable("userId") Long userId) {
        return userService.allBlocked(userId);
    }
}
