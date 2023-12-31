package com.example.airbnbb7.api;

import com.example.airbnbb7.db.customClass.SimpleResponse;
import com.example.airbnbb7.db.service.FavoriteHouseService;
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
@CrossOrigin(origins = "*")
@RequestMapping("/api/favorite")
@Tag(name = "Favorite House Api", description = "Favorite House Api")
public class FavoriteHouseApi {

    private final FavoriteHouseService favoriteHouseService;

    @PostMapping("/{houseId}")
    @Operation(summary = "SAVE FAVORITE HOUSE", description = "to houseId you must write house id that you want add to favorite" +
            "if you click you can add House to favorite," +
            "if you click another one you can put away House from Favorite")
    @PreAuthorize("hasAuthority('USER')")
    public SimpleResponse saveFavorite(@PathVariable Long houseId, Authentication authentication) {
        return favoriteHouseService.saveFavoriteHouse(houseId, authentication);
    }

    @GetMapping
    @Operation(summary = "GET ALL FAVORITE HOUSE", description = "This endpoint answer that get all house which you add to favorite")
    public List<HouseResponseSortedPagination> getAllFavoriteByUserId(Authentication authentication) {
        return favoriteHouseService.getAllFavoriteHouseByUserId(authentication);
    }
}
