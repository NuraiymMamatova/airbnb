package com.example.airbnbb7.db.repository;

import com.example.airbnbb7.db.entities.Role;
import com.example.airbnbb7.db.entities.User;
import com.example.airbnbb7.db.service.serviceImpl.UserServiceImpl;
import com.example.airbnbb7.dto.response.UserResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query("select u.roles from User u where u.email = :email")
    Role findRoleByUserEmail(String email);

    static Long getUserId() {
        return UserServiceImpl.getUserId();
    }

    @Query("select new com.example.airbnbb7.dto.response.UserResponse(u.id, u.name, u.email, u.image) from User u where u.id = :userId")
    UserResponse findUserById(Long userId);


}