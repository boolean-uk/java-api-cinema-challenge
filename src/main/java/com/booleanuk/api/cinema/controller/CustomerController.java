package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.response.ErrorResponse;
import com.booleanuk.api.cinema.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("customers")
public class CustomerController {

    @Autowired
    private CustomerRepository repository;

    @PostMapping
    public ResponseEntity<Response<?>> create(@RequestBody Customer customer) {
        customer.setTickets(new ArrayList<Ticket>());
        customer.setCreatedAt(String.valueOf(LocalDateTime.now()));
        customer.setUpdatedAt(customer.getCreatedAt());
        Response<Customer> customerResponse = new Response<>();
        customerResponse.set(this.repository.save(customer));
        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Response<?>> getAll() {
        Response<List<Customer>> customerResponse = new Response<>();
        customerResponse.set(this.repository.findAll());
        return ResponseEntity.ok(customerResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> update(@PathVariable int id, @RequestBody Customer customer) {
        Customer customerToUpdate = this.repository.findById(id).orElse(null);
        if(customerToUpdate == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        Optional.ofNullable(customer.getName())
                        .ifPresent(name -> customerToUpdate.setName(name));
        Optional.ofNullable(customer.getEmail())
                .ifPresent(email -> customerToUpdate.setEmail(email));
        Optional.ofNullable(customer.getPhone())
                .ifPresent(phone -> customerToUpdate.setPhone(phone));

        customerToUpdate.setUpdatedAt(String.valueOf(LocalDateTime.now()));
        Response<Customer> customerResponse = new Response<>();
        customerResponse.set(this.repository.save(customerToUpdate));
        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> delete(@PathVariable int id) {
        Customer customerToDelete = this.repository.findById(id).orElse(null);
        if(customerToDelete == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        this.repository.delete(customerToDelete);
        customerToDelete.setTickets(new ArrayList<>());
        Response<Customer> customerResponse = new Response<>();
        customerResponse.set(customerToDelete);
        return ResponseEntity.ok(customerResponse);
    }


}
