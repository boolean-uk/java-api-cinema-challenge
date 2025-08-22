package com.booleanuk.api.cinema.repositories;

import com.booleanuk.api.cinema.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Optional<Customer> findByName(String name);

    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
