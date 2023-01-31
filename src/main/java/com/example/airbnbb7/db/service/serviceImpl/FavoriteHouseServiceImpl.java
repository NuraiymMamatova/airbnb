package com.example.airbnbb7.db.service.serviceImpl;

import com.example.airbnbb7.db.entities.FavoriteHouse;
import com.example.airbnbb7.db.entities.House;
import com.example.airbnbb7.db.entities.User;
import com.example.airbnbb7.db.repository.FavoriteHouseRepository;
import com.example.airbnbb7.db.repository.HouseRepository;
import com.example.airbnbb7.db.repository.UserRepository;
import com.example.airbnbb7.db.service.FavoriteHouseService;
import com.example.airbnbb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavoriteHouseServiceImpl implements FavoriteHouseService {

    private final FavoriteHouseRepository favoriteHouseRepository;
    private final HouseRepository houseRepository;
    private final UserRepository userRepository;

    @Override
    public void saveFavoriteHouse(Long houseId,Long userId){
        House house = houseRepository.findById(houseId).orElseThrow(() -> new NotFoundException("house not found!"));
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("user not found!"));
        FavoriteHouse favoriteHouse = new FavoriteHouse(house,user);
        favoriteHouseRepository.save(favoriteHouse);
    }

    @Override
    public void deleteFavoriteHouse(Long id){
        favoriteHouseRepository.deleteById(id);
    }
}
