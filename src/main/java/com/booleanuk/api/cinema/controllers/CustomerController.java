package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Customer;
import com.booleanuk.api.cinema.repositories.CustomerRepository;
import com.booleanuk.api.cinema.responses.CustomerListResponse;
import com.booleanuk.api.cinema.responses.CustomerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {
        customer.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        customer.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        return new ResponseEntity<>(this.customerRepository.save(customer), HttpStatus.CREATED);
    }

    /*
    @GetMapping
    public ResponseEntity<CustomerListResponse> getAll() {
        CustomerListResponse customerListResponse = new CustomerListResponse();
        customerListResponse.set(customerRepository);
        return ResponseEntity.ok(customerListResponse);
    }
    */

    @GetMapping
    public List<Customer> getAll(){
        return this.customerRepository.findAll();
    }

    @GetMapping("{id}")
    //can return ResponseEntity<Customer>
    public ResponseEntity<CustomerResponse> getOne(@PathVariable int id) {
        Customer customer = this.customerRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No customer with that id")
        );
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.set(customer);
        return ResponseEntity.ok(customerResponse);
        //return ResponseEntity.ok(customer);
    }

    @PutMapping("{id}")
    public ResponseEntity<Customer> update(@PathVariable int id, @RequestBody Customer customer) {
        Customer customerToUpdate = this.customerRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No customer with that id")
        );
        customerToUpdate.setName(customer.getName());
        customerToUpdate.setEmail(customer.getEmail());
        customerToUpdate.setPhone(customer.getPhone());
        customerToUpdate.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        return new ResponseEntity<>(this.customerRepository.save(customerToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Customer> delete(@PathVariable int id) {
        Customer customerToDelete = this.customerRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No customer with that id")
        );
        this.customerRepository.delete(customerToDelete);
        return ResponseEntity.ok(customerToDelete);
    }
}
