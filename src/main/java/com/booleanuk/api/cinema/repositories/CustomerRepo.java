package com.booleanuk.api.cinema.repositories;

import com.booleanuk.api.cinema.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer,Integer> {
}
