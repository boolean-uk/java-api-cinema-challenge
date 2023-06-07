package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.DateTimeGenerator;
import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.repository.CustomerRepository;
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
    CustomerRepository customerRepository;

    public CustomerController() {}

    @GetMapping
    public List<Customer> getAll() {
        return this.customerRepository.findAll();
    }

    @GetMapping("/{id}")
    public Customer getById(@PathVariable("id") Integer id) {
        return this.customerRepository.findById(id).orElseThrow();
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        customer.setCreatedAt(DateTimeGenerator.getDateTimeNow());
        customer.setUpdatedAt(customer.getCreatedAt());
        return new ResponseEntity<Customer>(this.customerRepository.save(customer), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Integer id, @RequestBody Customer customer) {
        Customer customerToUpdate = this.customerRepository.findById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));

        customerToUpdate.setName(customer.getName());
        customerToUpdate.setEmail(customer.getEmail());
        customerToUpdate.setPhone(customer.getPhone());
        customerToUpdate.setUpdatedAt(DateTimeGenerator.getDateTimeNow());

        return new ResponseEntity<Customer>(this.customerRepository.save(customerToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable Integer id) {
        Customer customerToDelete = this.customerRepository.findById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        this.customerRepository.delete(customerToDelete);
        return ResponseEntity.ok(customerToDelete);
    }
}
