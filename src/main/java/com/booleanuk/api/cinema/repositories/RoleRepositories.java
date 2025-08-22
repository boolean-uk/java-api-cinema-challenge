package com.booleanuk.api.cinema.repositories;

import com.booleanuk.api.cinema.models.ERole;
import com.booleanuk.api.cinema.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepositories extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
}
