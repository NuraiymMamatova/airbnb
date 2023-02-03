package com.example.airbnbb7.db.service.serviceImpl;

import com.example.airbnbb7.db.customClass.Rating;
import com.example.airbnbb7.db.entities.FavoriteHouse;
import com.example.airbnbb7.db.entities.House;
import com.example.airbnbb7.db.entities.User;
import com.example.airbnbb7.db.repository.FavoriteHouseRepository;
import com.example.airbnbb7.db.repository.HouseRepository;
import com.example.airbnbb7.db.repository.LocationRepository;
import com.example.airbnbb7.db.repository.UserRepository;
import com.example.airbnbb7.db.service.FavoriteHouseService;
import com.example.airbnbb7.db.service.UserService;
import com.example.airbnbb7.dto.response.HouseResponseSortedPagination;
import com.example.airbnbb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteHouseServiceImpl implements FavoriteHouseService {

    private final FavoriteHouseRepository favoriteHouseRepository;
    private final HouseRepository houseRepository;
    private final Rating rating;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;

    private final UserService userService;

    @Override
    public void saveFavoriteHouse(Long houseId) {
        House house = houseRepository.findById(houseId).orElseThrow(()-> new NotFoundException("House not found!"));
        User user = userRepository.findById(userService.getUserId()).orElseThrow(()-> new NotFoundException("User not found!"));
        FavoriteHouse findFavoriteHouse = favoriteHouseRepository.getFavoriteHouseByHouseIdByUserId(house.getId(), user.getId());
        if (findFavoriteHouse == null) {
            FavoriteHouse favoriteHouse = new FavoriteHouse();
            house.setFavorite(true);
            favoriteHouse.setHouse(house);
            favoriteHouse.setUser(user);
            favoriteHouseRepository.save(favoriteHouse);
        } else {
            house.setFavorite(false);
            favoriteHouseRepository.delete(findFavoriteHouse);
        }
    }

    @Override
    public List<HouseResponseSortedPagination> getAllFavoriteHouseByUserId() {
        User user = userRepository.findById(userService.getUserId()).orElseThrow(() -> new NotFoundException("User not found!"));
        List<FavoriteHouse> favoriteHouses = user.getFavoriteHouses();
        List<House> houses = new ArrayList<>();
        for (FavoriteHouse f : favoriteHouses) {
            House house = f.getHouse();
            if (!houses.contains(house)) {
                houses.add(house);
            }
        }
        List<HouseResponseSortedPagination> houseResponseSortedPaginationList = new ArrayList<>();
        for (House house : houses) {
            HouseResponseSortedPagination houseResponseSortedPagination =
                    new HouseResponseSortedPagination(house.getId(), house.getPrice(), house.getTitle(),
                            house.getDescriptionOfListing(), house.getMaxOfGuests(), house.getHouseType(), house.isFavorite());
            houseResponseSortedPagination.setImages(house.getImages());
            houseResponseSortedPagination.setLocationResponse(locationRepository.convertToResponse(house.getLocation()));
            houseResponseSortedPagination.setHouseRating(rating.getRating(houseResponseSortedPagination.getId()));
            houseResponseSortedPaginationList.add(houseResponseSortedPagination);
        }
        return houseResponseSortedPaginationList;
    }
}
