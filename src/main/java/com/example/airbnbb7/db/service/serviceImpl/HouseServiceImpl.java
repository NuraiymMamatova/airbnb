package com.example.airbnbb7.db.service.serviceImpl;

import com.example.airbnbb7.converter.request.HouseRequestConverter;
import com.example.airbnbb7.db.customClass.Rating;
import com.example.airbnbb7.db.customClass.SimpleResponse;
import com.example.airbnbb7.db.entities.*;
import com.example.airbnbb7.db.enums.HouseType;
import com.example.airbnbb7.db.enums.HousesStatus;
import com.example.airbnbb7.db.repository.*;
import com.example.airbnbb7.db.service.AnnouncementService;
import com.example.airbnbb7.db.service.EmailService;
import com.example.airbnbb7.db.service.HouseService;
import com.example.airbnbb7.dto.request.HouseRequest;
import com.example.airbnbb7.dto.response.*;
import com.example.airbnbb7.exceptions.BadRequestException;
import com.example.airbnbb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService {

    private final BookingRepository bookingRepository;

    private final RoleRepository roleRepository;

    private final HouseRepository houseRepository;

    private final HouseRequestConverter houseRequestConverter;

    private final UserRepository userRepository;

    private final LocationRepository locationRepository;

    private final Rating rating;

    private final FeedbackRepository feedbackRepository;

    private final FavoriteHouseRepository favoriteHouseRepository;

    private final EmailService emailService;

    @Override
    public SimpleResponse deleteByIdHouse(Long houseId, Authentication authentication) {
        if (authentication != null) {
            User user = (User) authentication.getPrincipal();
            House house = houseRepository.findById(houseId).orElseThrow(() -> new NotFoundException("House id not found"));
            if (house.getOwner().getId() == user.getId() || roleRepository.findRoleByUserId(user.getId()).getId() == 1) {
                favoriteHouseRepository.deleteAll(favoriteHouseRepository.getAllFavoriteHouseByHouseId(houseId));
                houseRepository.delete(house);
            } else {
                throw new BadCredentialsException("You can't delete this announcement because it's not your announcement");
            }
            return new SimpleResponse("House successfully deleted");
        }
        throw new BadRequestException("Authentication cannot be null!");

    }

    @Override
    public SimpleResponse updateHouse(Long id, Authentication authentication, HouseRequest houseRequest) {
        if (authentication != null) {
            User user = (User) authentication.getPrincipal();
            House house = houseRepository.findById(id).orElseThrow(() -> new NotFoundException("House id not found"));
            if (house.getOwner().getId() == user.getId()) {
                house.setHousesStatus(HousesStatus.ON_MODERATION);
                houseRequestConverter.update(house, houseRequest);
                houseRepository.save(house);
            } else {
                throw new BadCredentialsException("You can't update this announcement because it's not your announcement");
            }
            return new SimpleResponse("House successfully updated!");
        }
        throw new BadRequestException("Authentication cannot be null!");
    }

    @Override
    public SimpleResponse save(HouseRequest houseRequest, Authentication authentication) {
        if (authentication != null) {
            User user = (User) authentication.getPrincipal();
            House house = new House(houseRequest.getPrice(), houseRequest.getTitle(), houseRequest.getDescriptionOfListing(), houseRequest.getMaxOfGuests(), houseRequest.getImages(), houseRequest.getHouseType());
            if (houseRequest.getLocation() != null) {
                Location location = new Location(houseRequest.getLocation().getAddress(), houseRequest.getLocation().getTownOrProvince(), houseRequest.getLocation().getRegion());
                location.setHouse(house);
                house.setLocation(location);
                house.setDateHouseCreated(LocalDate.now());
                house.setHousesStatus(HousesStatus.ON_MODERATION);
                house.setOwner(user);
                houseRepository.save(house);

                HouseResponse houseResponse = new HouseResponse(house.getId(), house.getPrice(), house.getTitle(),
                        house.getDescriptionOfListing(), house.getMaxOfGuests(), house.getHouseType());
                houseResponse.setOwner(new UserResponse(house.getOwner().getId(), house.getOwner().getName(),
                        house.getOwner().getEmail(), house.getOwner().getImage()));
                houseResponse.setLocation(new LocationResponse(house.getLocation().getId(), house.getLocation().getTownOrProvince(),
                        house.getLocation().getAddress(), house.getLocation().getRegion()));
                houseResponse.setImages(house.getImages());
            } else {
                throw new BadRequestException("Location cannot be null!");
            }
        } else {
            throw new BadRequestException("Authentication cannot be null!");
        }
        return new SimpleResponse("House successfully saved!");
    }

    @Override
    public List<AccommodationResponse> getLatestAccommodation(boolean popularHouse, boolean popularApartments) {

        if (popularHouse) {
            List<AccommodationResponse> houseResponses = houseRepository.getPopularHouse();
            for (AccommodationResponse accommodationResponse : houseResponses) {
                accommodationResponse.setLocationResponse(locationRepository.findLocationByHouseId(accommodationResponse.getId()).orElseThrow(() -> new NotFoundException("Location not found!")));
                accommodationResponse.setImages(houseRepository.findImagesByHouseId(accommodationResponse.getId()));
                accommodationResponse.setRating(rating.getRating(feedbackRepository.getAllFeedbackByHouseId(accommodationResponse.getId())));
            }
            houseResponses.sort(Comparator.comparing(AccommodationResponse::getRating).reversed());
            return houseResponses.stream().limit(3).toList();
        }
        if (popularApartments) {
            List<AccommodationResponse> popularApartmentByCountOfBookedUser = houseRepository.getPopularApartment();
            for (AccommodationResponse accommodationResponse : popularApartmentByCountOfBookedUser) {
                accommodationResponse.setLocationResponse(locationRepository.findLocationByHouseId(accommodationResponse.getId()).orElseThrow(() -> new NotFoundException("Location not found!")));
                accommodationResponse.setImages(houseRepository.findImagesByHouseId(accommodationResponse.getId()));
                accommodationResponse.setRating(rating.getRating(feedbackRepository.getAllFeedbackByHouseId(accommodationResponse.getId())));
            }
            popularApartmentByCountOfBookedUser.sort(Comparator.comparing(AccommodationResponse::getRating).reversed());
            return popularApartmentByCountOfBookedUser.stream().limit(7).toList();
        }
        List<AccommodationResponse> houseResponses = houseRepository.getLatestAccommodation();
        for (AccommodationResponse houseResponse : houseResponses) {
            House house = houseRepository.findById(houseResponse.getId()).orElseThrow(() -> new NotFoundException("House Id not found"));
            houseResponse.setImages(house.getImages());
            houseResponse.setLocationResponse(locationRepository.convertToResponse(house.getLocation()));
        }
        return houseResponses.stream().limit(7).toList();
    }

    @Override
    public ApplicationResponse getAllPagination(HouseType houseType, String sorting, String search, int page, int countOfHouses, String region, String popularAndLatest) {
        ApplicationResponse applicationResponse = new ApplicationResponse();
        Pageable pageable = PageRequest.of(page - 1, countOfHouses);
        List<HouseResponseSortedPagination> houseResponses = new ArrayList<>();
        if (search == null) {
            Page<HouseResponseSortedPagination> houseResponsePage = houseRepository.getAllResponse(pageable);
            houseResponses = houseResponsePage.getContent();
            applicationResponse.setPageSize(houseResponsePage.getTotalPages());
            houseResponses.forEach(h -> {
                House house = houseRepository.findById(h.getId()).orElseThrow(() -> new NotFoundException("House not found!"));
                Location location = house.getLocation();
                h.setLocationResponse(new LocationResponse(location.getId(), location.getTownOrProvince(),
                        location.getAddress(), location.getRegion()));
                h.setHouseRating(rating.getRating(house.getFeedbacks()));
            });

            houseResponses = filtering(houseResponses, houseType, region);
            houseResponses.forEach(h -> {
                House house = houseRepository.findById(h.getId()).orElseThrow(() -> new NotFoundException("House not found!"));
                Location location = house.getLocation();
                h.setImages(house.getImages());
                h.setLocationResponse(new LocationResponse(location.getId(), location.getTownOrProvince(),
                        location.getAddress(), location.getRegion()));
                h.setHouseRating(rating.getRating(house.getFeedbacks()));
            });
            houseResponses = sortHouse(houseResponses, sorting);
        } else {
            Page<HouseResponseSortedPagination> houseResponsePage = houseRepository.pagination(search, pageable);
            houseResponses = houseResponsePage.getContent();
            applicationResponse.setPageSize(houseResponsePage.getTotalPages());
        }
        houseResponses.forEach(h -> {
            House house = houseRepository.findById(h.getId()).orElseThrow(() -> new NotFoundException("House not found!"));
            Location location = house.getLocation();
            h.setImages(house.getImages());
            h.setLocationResponse(new LocationResponse(location.getId(), location.getTownOrProvince(),
                    location.getAddress(), location.getRegion()));
            h.setHouseRating(rating.getRating(house.getFeedbacks()));
        });
        applicationResponse.setPage((long) page);
        applicationResponse.setPaginationList(houseResponses);
        applicationResponse.setCountOfRegion(houseRepository.count(region));
        return applicationResponse;
    }

    public List<HouseResponseSortedPagination> sortHouse(List<HouseResponseSortedPagination> houseResponses, String sorting) {
        List<HouseResponseSortedPagination> sort = new ArrayList<>(houseResponses);
        if (sorting == null || sort.isEmpty()) {
            return houseResponses;
        }
        if ("Low to high".equals(sorting)) {
            sort.sort(Comparator.comparing(HouseResponseSortedPagination::getPrice));
        } else if ("High to low".equals(sorting)) {
            sort.sort(Comparator.comparing(HouseResponseSortedPagination::getPrice).reversed());
        }

        return sort;
    }

    private List<HouseResponseSortedPagination> filtering(List<HouseResponseSortedPagination> responseSortedPaginationList, HouseType houseType, String region) {
        List<HouseResponseSortedPagination> responses = responseSortedPaginationList;

        if (houseType != null) {
            switch (houseType) {
                case HOUSE -> {
                    responses = responses.stream().filter(x -> x.getHouseType() == HouseType.HOUSE).toList();
                }
                case APARTMENT -> {
                    responses = responses.stream().filter(x -> x.getHouseType() == HouseType.APARTMENT).toList();
                }
            }
        }

        if (region != null) {
            responses = responses.stream().filter(x -> x.getLocationResponse().getRegion().equals(region)).toList();
        }

        return responses;
    }

    @Override
    public AnnouncementService getAnnouncementById(Long houseId, Authentication authentication) {
        AnnouncementResponseForUser house = houseRepository.findHouseByIdForUser(houseId).orElseThrow(() -> new NotFoundException("House not found!"));
        UserResponse userResponse = userRepository.findUserById(houseRepository.findById(houseId).orElseThrow(() -> new NotFoundException("User not found!")).getOwner().getId());
        User user = null;
        if (authentication != null) {
            user = (User) authentication.getPrincipal();
        }
        house.setImages(houseRepository.findImagesByHouseId(houseId));
        house.setLocation(locationRepository.findLocationByHouseId(houseId).orElseThrow(() -> new NotFoundException("Location not found!")));
        List<FeedbackResponse> feedbackResponses = new ArrayList<>();
        List<Feedback> feedbacks = feedbackRepository.getAllFeedbackByHouseId(houseId);
        for (Feedback feedback : feedbacks) {
            FeedbackResponse feedbackResponse = feedbackRepository.findFeedbackByFeedbackId(feedback.getId());
            feedbackResponse.setOwner(feedbackRepository.findOwnerFeedbackByFeedbackId(feedback.getId()));
            feedbackResponses.add(feedbackResponse);
        }
        house.setFeedbacks(feedbackResponses);
        house.setRating(rating.getRatingCount(feedbacks));
        if (user != null) {
            List<Booking> bookings = bookingRepository.getBookingsByUserId(user.getId());
            house.setRating(rating.getRatingCount(feedbacks));
            for (Booking booking : bookings) {
                if (booking.getHouse().getId() == houseId) {
                    house.setBookingResponse(bookingRepository.findBookingById(booking.getId()).orElseThrow(() -> new NotFoundException("Location not found!")));
                }

            }
            if (userResponse.getId() == user.getId()) {
                AnnouncementResponseForVendor houseResponseForVendor = houseRepository.findHouseByIdForVendor(houseId).orElseThrow(() -> new NotFoundException("House not found!"));
                houseResponseForVendor.setImages(houseRepository.findImagesByHouseId(houseId));
                List<BookingResponse> bookingResponses = new ArrayList<>();
                for (BookingResponse booking : bookingRepository.getBookingsByHouseId(houseId)) {
                    booking.setOwner(userRepository.findUserById(bookingRepository.getUserIdByBookingId(booking.getId())));
                    bookingResponses.add(booking);
                }
                houseResponseForVendor.setRating(rating.getRatingCount(feedbacks));
                houseResponseForVendor.setBookingResponses(bookingResponses);
                houseResponseForVendor.setFeedbacks(feedbackResponses);
                houseResponseForVendor.setInFavorites(userRepository.inFavorite(houseId));
                houseResponseForVendor.setLocation(locationRepository.findLocationByHouseId(houseId).orElseThrow(() -> new NotFoundException("Location not found!")));
                return houseResponseForVendor;
            } else if (roleRepository.findRoleByUserId(user.getId()).getNameOfRole().equals("ADMIN")) {
                AnnouncementResponseForAdmin announcementResponseForAdmin = houseRepository.findHouseByIdForAdmin(houseId).orElseThrow(() -> new NotFoundException("House not found!"));
                announcementResponseForAdmin.setImages(houseRepository.findImagesByHouseId(houseId));
                announcementResponseForAdmin.setLocation(locationRepository.findLocationByHouseId(houseId).orElseThrow(() -> new NotFoundException("Location not found!")));
                announcementResponseForAdmin.setFeedbacks(feedbackResponses);
                announcementResponseForAdmin.setOwner(userResponse);
                announcementResponseForAdmin.setRating(rating.getRatingCount(feedbacks));
                return announcementResponseForAdmin;
            }
        }
        house.setOwner(userResponse);
        return house;
    }

    @Override
    public SimpleResponse changeStatusOfHouse(Long houseId, String message, HousesStatus housesStatus) {
        House house = houseRepository.findById(houseId).orElseThrow(() -> new NotFoundException("House not found!"));
        switch (housesStatus) {
            case ACCEPT -> {
                house.setHousesStatus(HousesStatus.ACCEPT);
                houseRepository.save(house);
                emailService.sendMessage(house.getOwner().getEmail(), String.format("House with title %s accepted :)", house.getTitle()), "Moderation successfully passed!");
                return new SimpleResponse("Accepted :)");
            }
            case REJECT -> {
                house.setHousesStatus(HousesStatus.REJECT);
                houseRepository.save(house);
                if (message != null) {
                    emailService.sendMessage(house.getOwner().getEmail(), String.format("House with title %s rejected :(", house.getTitle()), message);
                } else {
                    throw new BadRequestException("Message cannot be null!");
                }
                return new SimpleResponse("Successfully sent :)");
            }
            case BLOCKED -> {
                if (house.getHousesStatus().equals(HousesStatus.BLOCKED)) {
                    house.setHousesStatus(HousesStatus.ON_MODERATION);
                    houseRepository.save(house);
                    emailService.sendMessage(house.getOwner().getEmail(), String.format("House with title %s unblocked :)", house.getTitle()), "House unblocked :)");
                    return new SimpleResponse("Unblocked :)");
                } else {
                    house.setHousesStatus(HousesStatus.BLOCKED);
                    houseRepository.save(house);
                    emailService.sendMessage(house.getOwner().getEmail(), String.format("House with title %s blocked :(", house.getTitle()), "House blocked :(");
                    return new SimpleResponse("Blocked :)");
                }
            }
        }
        return new SimpleResponse();
    }

}
