package com.example.airbnbb7.repository;

import com.example.airbnbb7.db.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}