package com.example.airbnbb7.db.repository;

import com.example.airbnbb7.db.entities.FavoriteHouse;
import com.example.airbnbb7.db.entities.Role;
import com.example.airbnbb7.db.entities.User;
import com.example.airbnbb7.dto.response.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query("select u.roles from User u where u.email = :email")
    Role findRoleByUserEmail(String email);

    @Query("select new com.example.airbnbb7.dto.response.UserResponse(u.id, u.name, u.email, u.image) from User u where u.id = :userId")
    UserResponse findUserById(Long userId);

    @Query("select f from FavoriteHouse f where f.house.housesStatus = 2 and f.user.id = :userId")
    List<FavoriteHouse> getFavoriteHousesByUserId(Long userId);

    @Query("select new com.example.airbnbb7.dto.response.UserResponseForVendor(f.user.id, f.user.name, f.user.email, f.user.image, f.addedHouseToFavorites) from FavoriteHouse f where f.house.id = :houseId")
    List<UserResponseForVendor> inFavorite(Long houseId);

    @Query("select new com.example.airbnbb7.dto.response.UserAdminResponse(u.id, u.name, u.email, sum(h.bookings), count(h.id)) from User u, House h where u.id = h.owner.id group by u.id, u.name, u.email")
    List<UserAdminResponse> getAllUsers();

    @Query("select new com.example.airbnbb7.dto.response.ProfileAdminResponse(u.id, u.name, u.email) from User u where u.id =:userId")
    ProfileAdminResponse getUserByIdForAdmin(Long userId);

    @Query("select new com.example.airbnbb7.dto.response.HouseResponseForAdminUsers(h.id, h.price, h.title, " +
            "h.descriptionOfListing, h.maxOfGuests, h.houseType)" +
            "from House h JOIN h.bookingDates b JOIN b.users u WHERE u.id = :userId ")
    List<HouseResponseForAdminUsers> getBooking(Long userId);

    @Query("select new com.example.airbnbb7.dto.response.HouseResponseForAdminUsers(h.id, h.price, h.title, " +
            "h.descriptionOfListing, h.maxOfGuests, h.houseType)" +
            "from House h where h.owner.id = :userId")
    List<HouseResponseForAdminUsers> getUserByAnnouncement(Long userId);
}