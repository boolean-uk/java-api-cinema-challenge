package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.repository.CustomerRepository;
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

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        this.checkHasRequiredFields(customer);
        customer.setCreatedAt(ZonedDateTime.now());
        customer.setUpdatedAt(ZonedDateTime.now());
        return new ResponseEntity<>(this.customerRepository.save(customer), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return this.customerRepository.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        Customer customerToUpdate = this.findCustomerById(id);
        // 400 Bad request if no fields are present in the put request
        if (customer.getName() == null && customer.getEmail() == null && customer.getPhone() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the customer, please check all required fields are correct.");
        }
        if (customer.getName() != null) {
            customerToUpdate.setName(customer.getName());
        }
        if (customer.getEmail() != null) {
            customerToUpdate.setEmail(customer.getEmail());
        }
        if (customer.getPhone() != null) {
            customerToUpdate.setPhone(customer.getPhone());
        }
        customerToUpdate.setUpdatedAt(ZonedDateTime.now());
        return new ResponseEntity<>(this.customerRepository.save(customerToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable int id) {
        Customer customerToDelete = this.findCustomerById(id);
        this.customerRepository.delete(customerToDelete);
        return ResponseEntity.ok(customerToDelete);
    }

    // Method used in updateCustomer() and deleteCustomer() to find a customer by the id
    private Customer findCustomerById(int id) {
        return this.customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No customers with that id were found."));
    }

    // Method to check if all required fields are contained in the request, used in createCustomer()
    private void checkHasRequiredFields(Customer customer) {
        if (customer.getName() == null || customer.getEmail() == null || customer.getPhone() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please check all required fields are correct.");
        }
    }

}
