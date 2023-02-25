package com.example.airbnbb7.api;

import com.example.airbnbb7.db.service.AnnouncementService;
import com.example.airbnbb7.db.service.UserService;
import com.example.airbnbb7.dto.response.HouseResponseForAdminUsers;
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
    @Operation(summary = "User Profile", description = "With this method, you can see your houses and booked houses. Houses that are already being checked by the administrator.")
    public ProfileResponse userProfile(@RequestParam(required = false) String mainInUserProfile,
                                       @RequestParam(required = false) String sortHousesAsDesired,
                                       @RequestParam(required = false) String sortHousesByApartments,
                                       @RequestParam(required = false) String sortHousesByHouses,
                                       @RequestParam(required = false) String sortingHousesByValue,
                                       @RequestParam(required = false) String sortingHousesByRating,
                                       @RequestParam int page,
                                       @RequestParam int size,
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

    @GetMapping("/profileForAdmin")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Get user by id ", description = "User profile for administrator")
    public AnnouncementService getUserByIdDeleteAndBlock(@RequestParam Long userId,
                                                         @RequestParam(required = false) Long houseId,
                                                         @RequestParam(required = false) String bookingsOrAnnouncement,
                                                         @RequestParam(required = false) String blockAllAnnouncement,
                                                         @RequestParam(required = false) String deleteOrBlock) {
        return userService.getUserByIdDeleteAndBlock(userId,houseId,bookingsOrAnnouncement,blockAllAnnouncement,deleteOrBlock);
    }
}
