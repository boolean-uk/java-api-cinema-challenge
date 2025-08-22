package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Book;
import com.booleanuk.api.cinema.models.Customer;
import com.booleanuk.api.cinema.payloads.response.*;
import com.booleanuk.api.cinema.repositories.BookRepository;
import com.booleanuk.api.cinema.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Response<?>> createCustomer(@RequestBody Customer customer) {
        CustomerResponse customerResponse = new CustomerResponse();
        try {
            customerResponse.set(this.customerRepository.save(customer));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getCustomerById(@PathVariable int id) {
        Customer customer = this.customerRepository.findById(id).orElse(null);
        if (customer == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.set(customer);
        return ResponseEntity.ok(customerResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        Customer customerToUpdate = this.customerRepository.findById(id).orElse(null);
        if (customerToUpdate == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        customerToUpdate.setName(customer.getName());
        customerToUpdate.setEmail(customer.getEmail());
        customerToUpdate.setPhone(customer.getPhone());

        try {
            customerToUpdate = this.customerRepository.save(customerToUpdate);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.set(customerToUpdate);
        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteCustomer(@PathVariable int id) {
        Customer customerToDelete = this.customerRepository.findById(id).orElse(null);
        if (customerToDelete == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        this.customerRepository.delete(customerToDelete);
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.set(customerToDelete);
        return ResponseEntity.ok(customerResponse);
    }
}
