package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Customer;
import com.booleanuk.api.cinema.repositories.CustomerRepository;
import com.booleanuk.api.cinema.response.CustomerListResponse;
import com.booleanuk.api.cinema.response.CustomerResponse;
import com.booleanuk.api.cinema.response.ErrorResponse;
import com.booleanuk.api.cinema.response.Response;
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
    public ResponseEntity<CustomerListResponse> getAllCustomers() {
        CustomerListResponse customerListResponse = new CustomerListResponse();
        customerListResponse.set(this.customerRepository.findAll());
        return ResponseEntity.ok(customerListResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> addCustomer(@RequestBody Customer customer) {
        CustomerResponse customerResponse = new CustomerResponse();
        try {
            customerResponse.set(this.customerRepository.save(customer));
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("bad request");
            return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(customerResponse,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateCustomer(@RequestBody Customer customer, @PathVariable int id) {
        Customer updatedCustomer = null;
        try {
            updatedCustomer = this.customerRepository.findById(id).orElse(null);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad Request");
            return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
        }
        if (updatedCustomer == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Not Found");
            return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
        }
        updatedCustomer.setName(customer.getName());
        updatedCustomer.setEmail(customer.getEmail());
        updatedCustomer.setPhone(customer.getPhone());
        updatedCustomer.setUpdatedAt(java.time.LocalDateTime.now());
        updatedCustomer = this.customerRepository.save(updatedCustomer);
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.set(updatedCustomer);
        return new ResponseEntity<>(customerResponse,HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteCustomer(@PathVariable int id) {
        Customer customer = this.customerRepository.findById(id).orElse(null);
        if (customer == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Customer Not Found");
            return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
        }
        this.customerRepository.delete(customer);
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.set(customer);
        return ResponseEntity.ok(customerResponse);
    }
}
