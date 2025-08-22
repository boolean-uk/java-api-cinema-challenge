package com.booleanuk.api.cinema.repository;

import com.booleanuk.api.cinema.models.ERole;
import com.booleanuk.api.cinema.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
}
