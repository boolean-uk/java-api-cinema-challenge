package com.booleanuk.api.cinema.controller;


import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.response.ErrorResponse;
import com.booleanuk.api.cinema.response.CustomerListResponse;
import com.booleanuk.api.cinema.response.CustomerResponse;
import com.booleanuk.api.cinema.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("customers")
public class CustomerController {

    @Autowired
    private CustomerRepository repository;

    public CustomerController(CustomerRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<Object> getAll() {
        CustomerListResponse customerListResponse = new CustomerListResponse();
        customerListResponse.set(this.repository.findAll());
        return ResponseEntity.ok(customerListResponse);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Object> createCustomer(@RequestBody Customer customer) {
        customer.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        customer.setUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.set(this.repository.save(customer));
        return ResponseEntity.ok(customerResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updateCustomer(@PathVariable int id,
                                           @RequestBody Customer customer) {
        Customer customerToUpdate = this.repository.findById(id).orElse(null);
        if (customerToUpdate == null){
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        customerToUpdate.setName(customer.getName());
        customerToUpdate.setEmail(customer.getEmail());
        customerToUpdate.setPhone(customer.getPhone());
        customerToUpdate.setUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));

        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.set(this.repository.save(customerToUpdate));
        return ResponseEntity.ok(customerResponse);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> deleteCustomer(@PathVariable int id) {
        Customer customerToDelete = this.repository.findById(id).orElse(null);
        if (customerToDelete == null){
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        this.repository.delete(customerToDelete);
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.set(customerToDelete);
        return ResponseEntity.ok(customerResponse);
    }
}

