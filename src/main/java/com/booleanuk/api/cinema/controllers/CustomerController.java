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
    CustomerRepository repository;

    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {
        return new ResponseEntity<>(this.repository.save(customer), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAll() {
        return ResponseEntity.ok(this.repository.findAll());
    }

    @PutMapping("{id}")
    public ResponseEntity<Customer> update(
            @PathVariable int id,
            @RequestBody Customer customer)
    {
        Customer originalCustomer = this.getObjectById(id);
        customer.setId(id);
        customer.setCreatedAt(originalCustomer.getCreatedAt());
        return new ResponseEntity<>(this.repository.save(customer), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Customer> delete(@PathVariable int id) {
        Customer customer = getObjectById(id);
        try {
            this.repository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Could not delete customer. Detailed information: "+e.getMessage()
            );
        }
        return ResponseEntity.ok(customer);
    }

    /**
     * Get object by id.
     * Can be used to check for valid id (throws exception if id doesn't exist).
     * @param id .
     * @return Customer
     */
    private Customer getObjectById(int id) {
        Customer customer = this.repository
                .findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "No customer with id #"+id+" found."
                        )
                );
        return customer;
    }
}
