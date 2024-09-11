package com.booleanuk.api.cinema.repositories;

import com.booleanuk.api.cinema.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
