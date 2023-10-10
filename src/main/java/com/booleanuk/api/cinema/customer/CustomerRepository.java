package com.booleanuk.api.cinema.customer;

import com.booleanuk.api.cinema.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
