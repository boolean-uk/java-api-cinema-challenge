package com.booleanuk.api.cinema.repository;

import com.booleanuk.api.cinema.dto.CustomerDto;
import com.booleanuk.api.cinema.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    List<CustomerDto> findAllProjectedBy();
    CustomerDto findByIdProjectedBy(int id);
}
