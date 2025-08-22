package com.booleanuk.api.cinema.customer.repository;

import com.booleanuk.api.cinema.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
