package com.booleanuk.api.cinema.controller;


import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @GetMapping
    public List<Customer> getAll(){
        return customerRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getById(@PathVariable int id){
        Customer customer = customerRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No customer with that id were found"));
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Customer>  createCustomer(@RequestBody Customer customer){
        if(customer.getEmail() == null || customer.getName() == null || customer.getPhone() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create customer, please check all required fields are correct");
        }
        return new ResponseEntity<>(customerRepository.save(customer), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable int id, @RequestBody Customer customer){
        Customer customer1 = customerRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No customer with that id were found"));
        customer1.setName(customer.getName());
        customer1.setEmail(customer.getEmail());
        customer1.setPhone(customer.getPhone());
        customer1.setUpdatedAt(LocalDateTime.now(ZoneOffset.UTC));

        if(customer1.getName() == null || customer1.getPhone() == null || customer1.getEmail() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update customer, please check all required fields are correct");
        }

        return new ResponseEntity<>(customerRepository.save(customer1), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> delete(@PathVariable int id){
        Customer customer = customerRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No customer with that id were found"));
        customerRepository.delete(customer);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }
}
