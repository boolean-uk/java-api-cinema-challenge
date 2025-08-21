package com.booleanuk.api.cinema.library.repository;

import com.booleanuk.api.cinema.library.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
