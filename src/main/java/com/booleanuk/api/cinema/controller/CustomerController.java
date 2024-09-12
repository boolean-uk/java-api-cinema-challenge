package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Movie;
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
    private CustomerRepository customerRepository;

    @PostMapping
    public ResponseEntity<Customer> createMovie(@RequestBody Customer customer) {
        return new ResponseEntity<>(this.customerRepository.save(customer), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getCustomers(@RequestParam(required = false) String name) {
        if (name == null) {
            return ResponseEntity.ok(customerRepository.findAll());
        } else {
            List<Customer> customers = customerRepository.findAllByName(name);
            if (customers.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No customers found with that name");
            }
            return ResponseEntity.ok(customers);
        }
    }



    @PutMapping("{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        if (!existingCustomer.getEmail().equals(customer.getEmail()) &&
                customerRepository.existsByEmail(customer.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already in use");
        }
        existingCustomer.setName(customer.getName());
        existingCustomer.setEmail(customer.getEmail());
        existingCustomer.setPhone(customer.getPhone());

        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return ResponseEntity.ok(updatedCustomer);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable int id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        customerRepository.delete(customer);
        return ResponseEntity.ok(customer);
    }
}
