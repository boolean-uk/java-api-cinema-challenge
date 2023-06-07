package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public List<Customer> getAllCustomers(){
        return this.customerRepository.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable int id){
        Customer customer = this.customerRepository.findById(id).orElseThrow(() ->new ResponseStatusException(HttpStatus.NOT_FOUND,"Customer not found!"));
        return ResponseEntity.ok(customer);
    }
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        customer.setCreatedAt(LocalDateTime.now());
        Customer savedCustomer = customerRepository.save(customer);
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable int id, @RequestBody Customer customerDetails) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        return optionalCustomer.map(customer -> {
            customer.setName(customerDetails.getName());
            customer.setEmail(customerDetails.getEmail());
            customer.setPhone(customerDetails.getPhone());
            customer.setUpdatedAt(LocalDateTime.now());
            Customer updatedCustomer = customerRepository.save(customer);
            return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable int id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isPresent()) {
            customerRepository.delete(optionalCustomer.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    @PostMapping
//    public ResponseEntity<Customer>

}
