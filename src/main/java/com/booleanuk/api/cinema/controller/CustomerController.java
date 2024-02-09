package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.repository.CustomerRepository;
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

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        return ResponseEntity.ok(this.customerRepository.save(customer));
    }


    @GetMapping
    public List<Customer> getCustomers() {
        return this.customerRepository.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable (name = "id") int id, @RequestBody Customer customer) {
        Customer customerToUpdate = this.getACustomer(id);

        customerToUpdate.setName(customer.getName());
        customerToUpdate.setEmail(customer.getEmail());
        customerToUpdate.setUpdatedAt(customer.getUpdatedAt());
        customerToUpdate.setCreatedAt(customer.getCreatedAt());
        customerToUpdate.setPhone(customer.getPhone());

        return new ResponseEntity<>(this.customerRepository.save(customerToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable (name = "id") int id, @RequestBody Customer customer) {
        Customer customerToDelete = this.getACustomer(id);
        this.customerRepository.delete(customerToDelete);
        return new ResponseEntity<>(customerToDelete, HttpStatus.OK);
    }

    private Customer getACustomer(int id) {
        return this.customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Customers with that id were find."));
    }
}
