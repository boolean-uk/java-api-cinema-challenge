package com.booleanuk.api.cinema.controller;
import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.util.List;

//Imports skipped
@RestController
@RequestMapping("customers")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public List<Customer> getALlCustomers() {
        return this.customerRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        customer.setCreatedAt(LocalDateTime.now());
        return new ResponseEntity<>(this.customerRepository.save(customer), HttpStatus.CREATED);
    }

    @GetMapping("/{customer_id}")
    public ResponseEntity<Customer> getCustomerByCustomer_Id(@PathVariable int customer_customer_id) {
        return ResponseEntity.ok(this.getACustomer(customer_customer_id));
    }

    @DeleteMapping("/{customer_id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable int customer_customer_id) {
        Customer customerToDelete = this.getACustomer(customer_customer_id);
        this.customerRepository.delete(customerToDelete);
        return ResponseEntity.ok(customerToDelete);
    }

    @PutMapping("/{customer_id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable int customer_id, @RequestBody Customer customer) {
        Customer customerToUpdate = this.getACustomer(customer_id);
        customerToUpdate.setName(customer.getName());
        customerToUpdate.setEmail(customer.getEmail());
        customerToUpdate.setPhone(customer.getPhone());
        customerToUpdate.setUpdatedAt(LocalDateTime.now());
        return new ResponseEntity<>(this.customerRepository.save(customerToUpdate), HttpStatus.CREATED);
    }

    private Customer getACustomer(int customer_id) {
        return this.customerRepository.findById(customer_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
    }
}
