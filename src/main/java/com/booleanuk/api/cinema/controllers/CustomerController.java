package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Customer;
import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.repositories.CustomerRepository;
import com.booleanuk.api.cinema.respons_handling.Message;
import com.booleanuk.api.cinema.respons_handling.ResponseCreator;
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
    public ResponseEntity<ResponseCreator<?>> getAll() {
        return ResponseEntity.ok(new ResponseCreator<>("success", this.customerRepository.findAll()));
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseCreator<?>> getById(@PathVariable("id") Integer id) {
        Customer customer = this.customerRepository.findById(id).orElse(null);
        if (customer == null) {
            return new ResponseEntity<>(new ResponseCreator<>("error", new Message("Customer not found")) , HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(new ResponseCreator<>("success", customer));
    }

    @PostMapping
    public ResponseEntity<ResponseCreator<?>> create(@RequestBody Customer customer) {
        try {
            this.customerRepository.save(customer);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseCreator<>("error", new Message("At least one non-null field is null")) , HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseCreator<>("success", customer), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<ResponseCreator<?>> update(@PathVariable("id") Integer id, @RequestBody Customer customer) {
        Customer customerToUpdate = this.customerRepository.findById(id).orElse(null);
        if (customerToUpdate == null) {
            return new ResponseEntity<>(new ResponseCreator<>("error", new Message("Customer not found")) , HttpStatus.NOT_FOUND);
        }

        customerToUpdate.setName(customer.getName());
        customerToUpdate.setEmail(customer.getEmail());
        customerToUpdate.setPhone(customer.getPhone());
        try {
            this.customerRepository.save(customerToUpdate);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseCreator<>("error", new Message("At least one non-null field is null")) , HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new ResponseCreator<>("success", customerToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ResponseCreator<?>> deleteUser(@PathVariable("id") Integer id) {
        Customer customer = this.customerRepository.findById(id).orElse(null);
        if (customer == null) {
            return new ResponseEntity<>(new ResponseCreator<>("error", new Message("Customer not found")) , HttpStatus.NOT_FOUND);
        }
        if (!customer.getTickets().isEmpty()) {
            return new ResponseEntity<>(new ResponseCreator<>("error", new Message("Delete all tickets connected to the movie first")) , HttpStatus.BAD_REQUEST);
        }
        this.customerRepository.delete(customer);
        return ResponseEntity.ok(new ResponseCreator<>("success", customer));
    }
}
