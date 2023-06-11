package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
public class CustomerController {
    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("customers")
    public ResponseEntity<Iterable<Customer>> findAll() {
        return ResponseEntity.ok(this.customerRepository.findAll());
    }

    @PostMapping("customers")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = this.customerRepository.save(customer);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedCustomer.getId()).toUri();
        return ResponseEntity.created(location).body(savedCustomer);
    }

    @PutMapping("customers/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable long id, @RequestBody Customer customer) {
        Optional<Customer> customerOptional = this.customerRepository.findById(id);
        if (customerOptional.isEmpty()) return ResponseEntity.notFound().build();

        Customer customerToUpdate = customerOptional.get();

        customerToUpdate.setName(customer.getName());
        customerToUpdate.setEmail(customer.getEmail());
        customerToUpdate.setPhone(customerToUpdate.getPhone());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(customerToUpdate.getId()).toUri();

        return ResponseEntity.created(location).body(this.customerRepository.save(customerToUpdate));
    }

    @DeleteMapping("customers/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable long id) {
        Optional<Customer> customerOptional = this.customerRepository.findById(id);
        if (customerOptional.isEmpty()) return ResponseEntity.notFound().build();

        this.customerRepository.delete(customerOptional.get());
        return ResponseEntity.ok(customerOptional.get());
    }
}
