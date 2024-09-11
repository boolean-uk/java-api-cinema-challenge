package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController {

    @Autowired
    CustomerRepository repository;

    @GetMapping
    public ResponseEntity<List<Customer>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Customer> getById(@PathVariable Integer id) {
        Customer customer = getCustomer(id);
        return ResponseEntity.ok(customer);
    }

    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {
        customer.setCreatedAt(new Date());
        return new ResponseEntity<>(this.repository.save(customer), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Customer> update(@PathVariable Integer id, @RequestBody Customer updatedCustomer) {
        Customer customer = getCustomer(id);
        customer.setName(updatedCustomer.getName());
        customer.setEmail(updatedCustomer.getEmail());
        customer.setPhone(updatedCustomer.getPhone());
        customer.setUpdatedAt(new Date());

        //Get original value of when the customer was created
        customer.setCreatedAt(customer.getCreatedAt());

        return new ResponseEntity<>(this.repository.save(customer), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Customer> delete(@PathVariable Integer id) {
        Customer customer = getCustomer(id);
        this.repository.delete(customer);
        return ResponseEntity.ok(customer);
    }


    private Customer getCustomer(Integer id) {
        return this.repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find customer with ID"));
    }
}
