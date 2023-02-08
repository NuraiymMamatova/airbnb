package com.example.airbnbb7.api;

import com.example.airbnbb7.db.service.FavoriteHouseService;
import com.example.airbnbb7.dto.response.HouseResponseSortedPagination;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/favorite")
@Tag(name = "Favorite House Api", description = "Favorite House Api")
public class FavoriteHouseApi {

    private final FavoriteHouseService favoriteHouseService;

    @PostMapping("/{houseId}")
    public void saveFavorite(@PathVariable Long houseId) {
        favoriteHouseService.saveFavoriteHouse(houseId);
    }

    @GetMapping
    @Operation(summary = "get all favorite house", description = " this is get all favorite by userId")
    public List<HouseResponseSortedPagination> getAllFavoriteByUserId() {
        return favoriteHouseService.getAllFavoriteHouseByUserId();
    }
}