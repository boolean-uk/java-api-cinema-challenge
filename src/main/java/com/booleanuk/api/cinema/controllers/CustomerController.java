package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Customer;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.responses.CustomerListResponse;
import com.booleanuk.api.cinema.responses.CustomerResponse;
import com.booleanuk.api.cinema.responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController {

    private CustomerListResponse customerListResponse = new CustomerListResponse();
    private CustomerResponse customerResponse = new CustomerResponse();

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public ResponseEntity<Response<?>> getAllCustomers () {
        List<Customer> customers = customerRepository.findAll();
        this.customerListResponse.set(customers);

        return ResponseEntity.ok(customerListResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createCustomer(@RequestBody Customer customer){
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());
        this.customerResponse.set(customer);
        this.customerRepository.save(customer);

        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<?>> updateCustomer(@PathVariable int id, @RequestBody Customer customer){
        Customer customerToUpdate = this.customerRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found.."));

        customerToUpdate.setName(customer.getName());
        customerToUpdate.setEmail(customer.getEmail());
        customerToUpdate.setPhone(customer.getPhone());
        customerToUpdate.setUpdatedAt(LocalDateTime.now());
        this.customerResponse.set(customerToUpdate);
        this.customerRepository.save(customerToUpdate);

        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> deleteCustomer(@PathVariable int id){
        Customer customerToDelete = this.customerRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        this.customerResponse.set(customerToDelete);
        this.customerRepository.delete(customerToDelete);

        return ResponseEntity.ok(customerResponse);
    }
}
