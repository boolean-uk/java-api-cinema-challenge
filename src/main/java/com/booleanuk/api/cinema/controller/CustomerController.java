package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.response.CustomerResponse;
import com.booleanuk.api.cinema.response.ErrorResponse;
import com.booleanuk.api.cinema.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getCustomerById(@PathVariable int id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Customer not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        } else {
            CustomerResponse customerResponse = new CustomerResponse();
            customerResponse.set(customer);
            return ResponseEntity.ok(customerResponse);
        }
    }

    @PostMapping
    public ResponseEntity<Response<?>> createCustomer(@RequestBody Customer customer) {
        if (customer.getName() == null || customer.getEmail() == null || customer.getPhone() == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request. Name, email, and phone are required fields.");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        } else {
            Customer savedCustomer = customerRepository.save(customer);
            CustomerResponse customerResponse = new CustomerResponse();
            customerResponse.set(savedCustomer);
            return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        Customer existingCustomer = customerRepository.findById(id).orElse(null);
        if (existingCustomer == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Customer not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        } else {
            if (customer.getName() != null) {
                existingCustomer.setName(customer.getName());
            }
            if (customer.getEmail() != null) {
                existingCustomer.setEmail(customer.getEmail());
            }
            if (customer.getPhone() != null) {
                existingCustomer.setPhone(customer.getPhone());
            }
            Customer updatedCustomer = customerRepository.save(existingCustomer);
            CustomerResponse customerResponse = new CustomerResponse();
            customerResponse.set(updatedCustomer);
            return new ResponseEntity<>(customerResponse, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteCustomer(@PathVariable int id) {
        Customer existingCustomer = customerRepository.findById(id).orElse(null);
        if (existingCustomer == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Customer not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        } else {
            customerRepository.delete(existingCustomer);
            CustomerResponse customerResponse = new CustomerResponse();
            customerResponse.set(existingCustomer);
            return ResponseEntity.ok(customerResponse);
        }
    }
}
