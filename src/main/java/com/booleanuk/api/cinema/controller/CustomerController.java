package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequestMapping("customers")
@RestController
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    @PostMapping
    public ResponseEntity<Customer> createAuthor(@RequestBody Customer customer) {
        return new ResponseEntity<Customer>(this.customerRepository.save(customer), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Customer> getAll(){
        return this.customerRepository.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable int id, @RequestBody Customer customer){
        Customer customerToUpdate = this.customerRepository.findById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID Customer not found"));

        customerToUpdate.setName(customer.getName());
        customerToUpdate.setEmail(customer.getEmail());
        customerToUpdate.setPhone(customer.getPhone());
        customerToUpdate.setUpdatedAt(customer.getCreatedAt());

        return new ResponseEntity<Customer>(this.customerRepository.save(customerToUpdate), HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> deleteCostumer(@PathVariable int id){
        Customer customerToDelete = this.customerRepository.findById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID Customer not found"));
        this.customerRepository.delete(customerToDelete);
        return ResponseEntity.ok(customerToDelete);
    }

}
