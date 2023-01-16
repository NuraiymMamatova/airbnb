package com.example.airbnbb7.authwithgoogle.repository;

import com.example.airbnbb7.authwithgoogle.entity.AuthInfo;
import com.example.airbnbb7.db.entities.Role;
import com.example.airbnbb7.db.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthInfoRepository extends JpaRepository<AuthInfo, Long> {
    @Query("select a from AuthInfo a where a.email = :email")
    AuthInfo findByEmail(String email);

    @Query("select a.roles from AuthInfo a where a.user = :user")
    Role findRoleByUser(User user);

}