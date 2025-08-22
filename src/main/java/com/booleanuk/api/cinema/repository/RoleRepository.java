package com.booleanuk.api.cinema.repository;

import com.booleanuk.api.cinema.models.ERole;
import com.booleanuk.api.cinema.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
    Boolean existsByName(ERole name);
}
