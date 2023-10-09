package com.booleanuk.api.Cinema.Repository;

import com.booleanuk.api.Cinema.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
