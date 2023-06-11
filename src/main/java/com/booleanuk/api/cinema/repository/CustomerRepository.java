package com.booleanuk.api.cinema.repository;

import com.booleanuk.api.cinema.model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
