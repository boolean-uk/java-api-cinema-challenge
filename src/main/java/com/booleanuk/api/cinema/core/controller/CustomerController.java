package com.booleanuk.api.cinema.core.controller;

import com.booleanuk.api.cinema.core.model.Customer;
import com.booleanuk.api.cinema.core.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    CustomerRepository customerRepository;

    @GetMapping
    public List<Customer> getAllCustomers() {
        return this.customerRepository.findAll();
    };

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        return new ResponseEntity<Customer>(this.customerRepository.save(customer), HttpStatus.CREATED);
    }




}
