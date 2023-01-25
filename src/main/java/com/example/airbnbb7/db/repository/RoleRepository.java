package com.example.airbnbb7.db.repository;

import com.example.airbnbb7.db.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("select r from Role r where r.nameOfRole = :name")
    Role findByName(String name);
}