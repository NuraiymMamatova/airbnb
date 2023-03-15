package com.example.airbnbb7.db.service.serviceImpl;

import com.example.airbnbb7.db.customClass.Rating;
import com.example.airbnbb7.db.customClass.SimpleResponse;
import com.example.airbnbb7.db.entities.FavoriteHouse;
import com.example.airbnbb7.db.entities.House;
import com.example.airbnbb7.db.entities.User;
import com.example.airbnbb7.db.repository.*;
import com.example.airbnbb7.db.service.FavoriteHouseService;
import com.example.airbnbb7.db.service.HouseService;
import com.example.airbnbb7.dto.response.HouseResponseSortedPagination;
import com.example.airbnbb7.exceptions.BadCredentialsException;
import com.example.airbnbb7.exceptions.BadRequestException;
import com.example.airbnbb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FavoriteHouseServiceImpl implements FavoriteHouseService {

    private final FavoriteHouseRepository favoriteHouseRepository;

    private final HouseRepository houseRepository;

    private final Rating rating;

    private final UserRepository userRepository;

    private final LocationRepository locationRepository;

    private final FeedbackRepository feedbackRepository;

    private final HouseService houseService;

    @Override
    public SimpleResponse saveFavoriteHouse(Long houseId, Authentication authentication) {
        House house = houseRepository.findById(houseId).orElseThrow(() -> new NotFoundException("House not found!"));
        if (authentication != null) {
            User user = (User) authentication.getPrincipal();
            if (house.getOwner().getId() != user.getId()) {
                FavoriteHouse findFavoriteHouse = favoriteHouseRepository.getFavoriteHouseByHouseIdByUserId(house.getId(), user.getId());
                if (findFavoriteHouse == null) {
                    FavoriteHouse favoriteHouse = new FavoriteHouse();
                    house.setFavorite(true);
                    favoriteHouse.setAddedHouseToFavorites(LocalDate.now());
                    favoriteHouse.setHouse(house);
                    favoriteHouse.setUser(user);
                    log.info("successfully save favorite house");
                    favoriteHouseRepository.save(favoriteHouse);
                } else {
                    house.setFavorite(false);
                    favoriteHouseRepository.delete(findFavoriteHouse);
                    log.info("House id {} successfully deleted from favorite", house.getId());
                    return new SimpleResponse("House successfully deleted from favorite!");
                }
            } else {
                log.error("can't added house {} to favorite", house.getId());
                throw new BadCredentialsException("You can't added house to favorite because it's your announcement!");
            }
        } else {
            log.error("The Authentication {} null", authentication.getPrincipal());
            throw new BadRequestException("Authentication cannot be null!");
        }
        return new SimpleResponse("House successfully added to favorite!");
    }

    @Override
    public List<HouseResponseSortedPagination> getAllFavoriteHouseByUserId(Authentication authentication) {
        if (authentication != null) {
            User user = (User) authentication.getPrincipal();
            List<FavoriteHouse> favoriteHouses = userRepository.getFavoriteHousesByUserId(user.getId());
            List<House> houses = new ArrayList<>();
            for (FavoriteHouse f : favoriteHouses) {
                House house = f.getHouse();
                houses.add(house);
            }
            List<HouseResponseSortedPagination> houseResponseSortedPaginationList = new ArrayList<>();
            for (House house : houses) {
                HouseResponseSortedPagination houseResponseSortedPagination =
                        new HouseResponseSortedPagination(house.getId(), house.getPrice(), house.getTitle(),
                                house.getDescriptionOfListing(), house.getMaxOfGuests(), house.getHouseType(), house.isFavorite());
                houseResponseSortedPagination.setImages(houseService.getImagesAndIdByHouseId(house.getId()));
                houseResponseSortedPagination.setLocationResponse(locationRepository.convertToResponse(house.getLocation()));
                houseResponseSortedPagination.setHouseRating(rating.getRating(feedbackRepository.getAllFeedbackByHouseId(house.getId())));
                houseResponseSortedPaginationList.add(houseResponseSortedPagination);
            }
            log.info("sorted house");
            return houseResponseSortedPaginationList;
        } else {
            log.error("The Authentication {} null", authentication.getPrincipal());
            throw new BadRequestException("Authentication cannot be null!");
        }
    }
}
