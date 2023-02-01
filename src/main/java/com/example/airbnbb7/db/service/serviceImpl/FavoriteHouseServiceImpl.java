package com.example.airbnbb7.db.service.serviceImpl;

import com.example.airbnbb7.db.entities.FavoriteHouse;
import com.example.airbnbb7.db.entities.House;
import com.example.airbnbb7.db.entities.User;
import com.example.airbnbb7.db.repository.FavoriteHouseRepository;
import com.example.airbnbb7.db.repository.HouseRepository;
import com.example.airbnbb7.db.repository.UserRepository;
import com.example.airbnbb7.db.service.FavoriteHouseService;
import com.example.airbnbb7.dto.response.HouseResponseSortedPagination;
import com.example.airbnbb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteHouseServiceImpl implements FavoriteHouseService {

    private final FavoriteHouseRepository favoriteHouseRepository;
    private final HouseRepository houseRepository;
    private final UserRepository userRepository;

    @Override
    public void saveFavoriteHouse(Long houseId,Long userId) {
        House house = houseRepository.findById(houseId).orElseThrow(() -> new NotFoundException("House not found!"));
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("user not found!"));
        if (house.isFavorite()) {
            favoriteHouseRepository.deleteById(favoriteHouseRepository.getIdFavoriteHouseByHouseIdByUserId(houseId, userId));
        } else {
            house.setFavorite(true);
            favoriteHouseRepository.save(new FavoriteHouse(house, user));
        }
    }

    @Override
    public List<HouseResponseSortedPagination> getAllFavoriteHouse() {
        return null;
    }
}
