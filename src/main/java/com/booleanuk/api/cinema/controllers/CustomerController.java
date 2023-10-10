package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Customer;
import com.booleanuk.api.cinema.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public List<Customer> getAllCustomers() {
        return this.customerRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
        customer.setCreatedAt(java.time.LocalDateTime.now());
        customer.setUpdatedAt(null);
        return new ResponseEntity<>(this.customerRepository.save(customer), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer, @PathVariable int id) {
        Customer updatedCustomer = this.customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Customer Not Found"));
        updatedCustomer.setName(customer.getName());
        updatedCustomer.setEmail(customer.getEmail());
        updatedCustomer.setPhone(customer.getPhone());
        updatedCustomer.setUpdatedAt(java.time.LocalDateTime.now());
        this.customerRepository.save(updatedCustomer);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable int id) {
        Customer customer = this.customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Customer Not Found"));
        this.customerRepository.delete(customer);
        return ResponseEntity.ok(customer);
    }
}
