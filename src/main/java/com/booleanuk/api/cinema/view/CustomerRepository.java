package com.booleanuk.api.cinema.view;


import com.booleanuk.api.cinema.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}