package com.example.airbnbb7.api;

import com.example.airbnbb7.db.entities.User;
import com.example.airbnbb7.db.service.UserService;
import com.example.airbnbb7.dto.response.ProfileResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "User Api", description = "User Api")
public class UserApi {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "User Profile", description = "With this method, you can see your houses and booked houses. Houses that are already being checked by the administrator." +
            "houseSorting = houseType, sortingHousesByValue = price, sortingHousesByRating = rating")
    public ProfileResponse userProfile(@RequestParam(name = "mainInUserProfile", required = false) String mainInUserProfile,
                                       @RequestParam(name = "houseSorting", required = false)String houseSorting,
                                       @RequestParam(name = "sortingHousesByValue", required = false)String sortingHousesByValue,
                                       @RequestParam(name = "sortingHousesByRating", required = false)String sortingHousesByRating,
                                       @RequestParam(name = "paginationSize")int paginationSize,
                                       Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return userService.userProfile(mainInUserProfile, houseSorting, sortingHousesByValue, sortingHousesByRating, user.getId(),paginationSize);
    }
}
