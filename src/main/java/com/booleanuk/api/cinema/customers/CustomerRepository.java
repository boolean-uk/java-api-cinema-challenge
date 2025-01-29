package com.booleanuk.api.cinema.customers;

import com.booleanuk.api.cinema.customers.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{
}
