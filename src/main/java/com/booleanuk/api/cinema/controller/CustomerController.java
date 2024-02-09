package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.dto.CustomerDto;
import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController {
    @Autowired
    CustomerRepository repository;

    @GetMapping
    public List<CustomerDto> getAllCustomers() {
        return this.repository.findAllProjectedBy();
    }

    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody Customer customer) {
        if (customer.getEmail() == null || customer.getName() == null || customer.getPhone() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
        }
        Customer createdCustomer = this.repository.save(customer);
        createdCustomer.setTickets(new ArrayList<>());
        return new ResponseEntity<>(this.translateToDto(createdCustomer), HttpStatus.CREATED);
    }

    public CustomerDto translateToDto(Customer customer) {
        return new CustomerDto(customer.getId(), customer.getName(), customer.getEmail(), customer.getPhone(), customer.getCreatedAt(), customer.getUpdatedAt());
    }
}
