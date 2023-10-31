package com.booleanuk.api.cinema.Repository;

import com.booleanuk.api.cinema.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}