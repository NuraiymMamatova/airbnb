package com.example.airbnbb7.db.service.serviceImpl;

import com.example.airbnbb7.converter.request.HouseRequestConverter;
import com.example.airbnbb7.converter.response.HouseResponseConverter;
import com.example.airbnbb7.db.entities.House;
import com.example.airbnbb7.db.entities.User;
import com.example.airbnbb7.db.enums.HousesStatus;
import com.example.airbnbb7.db.repository.HouseRepository;
import com.example.airbnbb7.db.repository.UserRepository;
import com.example.airbnbb7.db.service.HouseService;
import com.example.airbnbb7.dto.request.HouseRequest;
import com.example.airbnbb7.dto.response.HouseResponse;
import com.example.airbnbb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService {

    private final HouseRepository houseRepository;

    private final HouseRequestConverter houseRequestConverter;

    private final HouseResponseConverter houseResponseConverter;

    private final UserRepository userRepository;
    @Override
    public HouseResponse saveHouse(HouseRequest houseRequest, Long id) {
        House house = houseRequestConverter.saveHouse(houseRequest);
        house.setDateHouseCreated(LocalDate.now());
        User byId = userRepository.findById(id).orElseThrow(()->new NotFoundException("not found user"));
        byId.addHouse(house);
        house.setOwner(byId);
        house.setHousesStatus(HousesStatus.ON_MODERATION);
        houseRepository.save(house);
        return houseResponseConverter.viewHouse(house);
    }

    @Override
    public HouseResponse deleteByIdHouse(Long houseId) {
        House house = houseRepository.findById(houseId).get();
        houseRepository.delete(house);
        return houseResponseConverter.viewHouse(houseRepository.save(house));
    }


}
