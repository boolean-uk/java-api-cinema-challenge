package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.response.CustomerListResponse;
import com.booleanuk.api.cinema.response.CustomerResponse;
import com.booleanuk.api.cinema.response.ErrorResponse;
import com.booleanuk.api.cinema.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public ResponseEntity<CustomerListResponse> getAllCustomer(){
        CustomerListResponse response = new CustomerListResponse();
        response.set(this.customerRepository.findAll());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createCustomer(@RequestBody Customer customer){
        Customer customer1;
        try {
            customer1 = this.customerRepository.save(customer);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Could not create a new customer, please check all fields are correct.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.set(customer1);
        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateCustomer(@PathVariable int id, @RequestBody Customer customer){
        Customer customer1 = this.getACustomer(id);
        if (customer1 == null){
            ErrorResponse error = new ErrorResponse();
            error.set("No customer with that id found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        } try {
            customer1.setName(customer.getName());
            customer1.setEmail(customer.getEmail());
            customer1.setPhone(customer.getPhone());
            customer1.setUpdatedAt(ZonedDateTime.now());
            this.customerRepository.save(customer1);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Could not update customer, please check all fields are correct.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.set(customer1);
        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteCustomer(@PathVariable int id){
        Customer customer1 = this.getACustomer(id);
        if (customer1 == null){
            ErrorResponse error = new ErrorResponse();
            error.set("No customer with that id found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        this.customerRepository.delete(customer1);
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.set(customer1);
        return ResponseEntity.ok(customerResponse);
    }

    private Customer getACustomer(int id){
        return this.customerRepository.findById(id).orElse(null);
    }
}
