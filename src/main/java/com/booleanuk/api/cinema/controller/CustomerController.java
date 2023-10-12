package com.booleanuk.api.cinema.controller;


import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository repository) {
        this.customerRepository = repository;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Customer customerCreated(@RequestBody Customer newCustomer) throws SQLException {
        OffsetDateTime now = OffsetDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        String timestamp = now.format(formatter);

        newCustomer.setCreatedAt(timestamp);
        newCustomer.setUpdatedAt(timestamp);
        return this.customerRepository.save(newCustomer);
    }

    @GetMapping
    public List<Customer> getAll() {
        return this.customerRepository.findAll();
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable int id) {
        Customer customer = null;
        customer = this.customerRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer was not found")
        );
        return customer;
    }


    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        Customer customerToUpdate = this.customerRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer was not found")
        );

        //////////////////////////////////////////////////////////////////////////
        //Set date for new customer
        OffsetDateTime now = OffsetDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        String timestamp = now.format(formatter);
        customer.setUpdatedAt(timestamp);
        //////////////////////////////////////////////////////////////////////////

        customerToUpdate.setEmail(customer.getEmail());
        customerToUpdate.setName(customer.getName());
        customerToUpdate.setPhone(customer.getPhone());
        customerToUpdate.setUpdatedAt(timestamp);
        return new ResponseEntity<>(this.customerRepository.save(customerToUpdate), HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> deleteCustomerById(@PathVariable int id) {
        Customer customerToDelete = this.customerRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer was not found")
        );

        customerRepository.delete(customerToDelete);
        //ResponseEntity.ok stuurt een status code 200 terug, met customerToDelete als value
        //In echte projecten 'return ResponseEntity.noContent().build();' gebruiken ipv .ok
        //Dit stuurt een code 204 terug , 204 = no content
        return ResponseEntity.ok(customerToDelete);
    }


}
