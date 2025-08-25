package com.booleanuk.api.cinema.repository;

import com.booleanuk.api.cinema.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}