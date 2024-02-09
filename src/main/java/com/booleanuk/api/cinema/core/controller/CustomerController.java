package com.booleanuk.api.cinema.core.controller;

import com.booleanuk.api.cinema.core.model.Customer;
import com.booleanuk.api.cinema.core.repository.CustomerRepository;
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
        return customerRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") int id) {
        Customer customer = customerRepository
                .findById((long) id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        return ResponseEntity.ok(customer);
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        checkIfCustomerIsValid(customer);
        Customer newCustomer = customerRepository.save(customer);
        return ResponseEntity.ok(newCustomer);
    }

    @PutMapping("{id}")
    public Customer updateCustomer(@PathVariable("id") int id, @RequestBody Customer customer) {
        checkIfCustomerIsValid(customer);
        Customer customerToUpdate = customerRepository
                .findById((long) id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        if (customerToUpdate != null) {
            customerToUpdate.setName(customer.getName());
            customerRepository.save(customerToUpdate);
        }
        return customerToUpdate;
    }

    @DeleteMapping("{id}")
    public Customer deleteCustomer(@PathVariable("id") int id) {
        Customer customerToDelete = customerRepository
                .findById((long) id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        if (customerToDelete != null) {
            customerRepository.delete(customerToDelete);
        }
        return customerToDelete;
    }

    private void checkIfCustomerIsValid(Customer customer) {
        if (customer.getName() == null || customer.getName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name is required");
        } else if (customer.getEmail() == null || customer.getEmail().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is required");
        } else if (customer.getPhone() == null || customer.getPhone().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Phone number is required");
        }
    }
}
