package com.example.airbnbb7.db.repository;

import com.example.airbnbb7.db.entities.Role;
import com.example.airbnbb7.db.entities.User;
import com.example.airbnbb7.dto.response.user.UserResponse;
import com.example.airbnbb7.dto.response.user.UserResponseForVendor;
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

    @Query("select new com.example.airbnbb7.dto.response.user.UserResponse(u.id, u.name, u.email, u.image) from User u where u.id = :userId")
    UserResponse findUserById(Long userId);

    @Query("select new com.example.airbnbb7.dto.response.user.UserResponseForVendor(f.user.id, f.user.name, f.user.email, f.user.image, f.addedHouseToFavorites) from FavoriteHouse f where f.house.id = :houseId")
    List<UserResponseForVendor> inFavorite(Long houseId);

}