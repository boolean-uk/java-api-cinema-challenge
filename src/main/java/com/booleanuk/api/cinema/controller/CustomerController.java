package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.responses.CustomerListResponse;
import com.booleanuk.api.cinema.responses.CustomerResponse;
import com.booleanuk.api.cinema.responses.ErrorResponse;
import com.booleanuk.api.cinema.responses.Response;
import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController {

    @Autowired
    CustomerRepository repository;

    @GetMapping
    public ResponseEntity<Response<?>> getAll() {
        List<Customer> customers = ResponseEntity.ok(repository.findAll()).getBody();
        CustomerListResponse customerListResponse = new CustomerListResponse();
        customerListResponse.set(customers);
        return ResponseEntity.ok(customerListResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<?>> getById(@PathVariable Integer id) {
        Customer customer = this.repository.findById(id).orElse(null);
        if(customer == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Not found!");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.set(customer);
        return ResponseEntity.ok(customerResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> create(@RequestBody Customer customer) {
        if(customer.getName() == null || customer.getEmail() == null || customer.getPhone() == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("bad request");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        customer.setCreatedAt(new Date());
        customer.setUpdatedAt(new Date());
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.set(customer);
        this.repository.save(customer);
        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<?>> update(@PathVariable Integer id, @RequestBody Customer updatedCustomer) {
        if(updatedCustomer.getName() == null || updatedCustomer.getEmail() == null || updatedCustomer.getPhone() == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("bad request");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        Customer customer = this.repository.findById(id).orElse(null);
        if(customer == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Not found!");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        customer.setName(updatedCustomer.getName());
        customer.setEmail(updatedCustomer.getEmail());
        customer.setPhone(updatedCustomer.getPhone());
        customer.setUpdatedAt(new Date());

        //Get original value of when the customer was created
        customer.setCreatedAt(customer.getCreatedAt());

        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.set(customer);

        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Customer customer = this.repository.findById(id).orElse(null);
        if(customer == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Not found!");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.set(customer);
        this.repository.delete(customer);
        return ResponseEntity.ok(customerResponse);
    }

}

