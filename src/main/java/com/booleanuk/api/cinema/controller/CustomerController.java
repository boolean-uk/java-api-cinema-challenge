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

    @GetMapping
    public List<Customer> getAll() {
        return this.customerRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        if(containsNull(customer)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create customer, please check all required fields are correct.");
        }
        return new ResponseEntity<>(customerRepository.save(customer), HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable int id) {
        Customer customer = findCustomer(id);
        return ResponseEntity.ok(customer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable int id) {
        Customer customerToDelete = findCustomer(id);

        customerRepository.delete(customerToDelete);
        return ResponseEntity.ok(customerToDelete);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        Customer customerToUpdate = findCustomer(id);
        if(containsNull(customer)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the customer, please check all required fields are correct.");
        }
        customerToUpdate.setName(customer.getName());
        customerToUpdate.setPhone(customer.getPhone());
        customerToUpdate.setEmail(customer.getEmail());

        return new ResponseEntity<>(customerRepository.save(customerToUpdate), HttpStatus.CREATED);
    }

    private Customer findCustomer(int id) {
        return customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No customers with that id were found"));
    }

    private boolean containsNull(Customer customer) {
        return customer.getName() == null || customer.getPhone() == null || customer.getEmail() == null;
    }
}


