package com.example.airbnbb7.api;

import com.example.airbnbb7.db.service.FavoriteHouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/favorite")
public class FavoriteHouseApi {

    private final FavoriteHouseService favoriteHouseService;

    @PostMapping("/{houseId}/{userId}")
    public void saveFavorite(@PathVariable Long houseId,
                             @PathVariable Long userId){
        favoriteHouseService.saveFavoriteHouse(houseId,userId);
    }

    @GetMapping("/{favoriteHouseId}")
    public void deleteFavorite(@PathVariable Long favoriteHouseId){
        favoriteHouseService.deleteFavoriteHouse(favoriteHouseId);
    }
}
