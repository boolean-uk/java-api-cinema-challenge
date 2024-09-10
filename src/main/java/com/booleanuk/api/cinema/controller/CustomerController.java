package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.repository.CustomerRepository;

import java.time.LocalDateTime;

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
    private CustomerRepository customers;

    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody Customer toAdd) {
        toAdd.setCreatedAt(LocalDateTime.now());
        toAdd.setUpdatedAt(LocalDateTime.now());
        return new ResponseEntity<>(this.customers.save(toAdd), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAll() {
        return new ResponseEntity<>(this.customers.findAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Customer> getOne(@PathVariable int id) {
        Customer toReturn = this.customers.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
                );
        return new ResponseEntity<>(toReturn, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Customer> delete(@PathVariable int id) {
        Customer toDelete = this.customers.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
                );
        this.customers.delete(toDelete);
        return new ResponseEntity<>(toDelete, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Customer> update(@PathVariable int id, @RequestBody Customer newData) {
        Customer toUpdate = this.customers.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
                );

        toUpdate.setName(newData.getName());
        toUpdate.setEmail(newData.getEmail());
        toUpdate.setPhone(newData.getPhone());

        toUpdate.setUpdatedAt(LocalDateTime.now());

        return new ResponseEntity<>(this.customers.save(toUpdate), HttpStatus.CREATED);
    }

}
