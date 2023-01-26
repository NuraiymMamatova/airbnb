package com.example.airbnbb7.db.service.serviceImpl;

import com.example.airbnbb7.db.entities.FavoriteHouse;
import com.example.airbnbb7.db.repository.FavoriteHouseRepository;
import com.example.airbnbb7.db.repository.HouseRepository;
import com.example.airbnbb7.db.repository.UserRepository;
import com.example.airbnbb7.db.service.FavoriteHouseService;
import com.example.airbnbb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class FavoriteHouseServiceImpl implements FavoriteHouseService {

    private final FavoriteHouseRepository favoriteHouseRepository;

    private final HouseRepository houseRepository;

    private final UserRepository userRepository;

    @Override
    public void addHouseToFavorite(Long houseId, Long userId) {
        FavoriteHouse favoriteHouse = new FavoriteHouse();
        favoriteHouse.setUser(userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found!")));
        favoriteHouse.setHouse(houseRepository.findById(houseId).orElseThrow(() -> new NotFoundException("House not found!")));
        favoriteHouse.setAddedHouseToFavorites(LocalDate.now());
        favoriteHouseRepository.save(favoriteHouse);
    }

}
