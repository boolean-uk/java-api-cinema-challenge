package com.booleanuk.api.cinema.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        customer.setCreated_at(String.valueOf(LocalDateTime.now()));
        return new ResponseEntity<>(this.customerRepository.save(customer), HttpStatus.CREATED);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Customer> getAllCustomers() {
        return this.customerRepository.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        Customer updatedCustomer = this.customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No customer with that id"));

        updatedCustomer.setName(customer.getName());
        updatedCustomer.setEmail(customer.getEmail());
        updatedCustomer.setPhone(customer.getPhone());
        updatedCustomer.setUpdated_at(String.valueOf(LocalDateTime.now()));

        return new ResponseEntity<>(this.customerRepository.save(updatedCustomer),HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable int id) {
        Customer deletedCustomer = this.customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No customer with that id"));
        this.customerRepository.delete(deletedCustomer);
        return ResponseEntity.ok(deletedCustomer);
    }
}
