package com.booleanuk.api.cinema.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public List<Customer> getAllCustomers() {
        return this.customerRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        if (customer.getName() == null || customer.getEmail() == null || customer.getPhone() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Some fields are invalid");
        }

        Date today = new Date();
        customer.setCreatedAt(today);
        customer.setUpdatedAt(today);

        return new ResponseEntity<>(this.customerRepository.save(customer), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        if (customer.getName() == null || customer.getEmail() == null || customer.getPhone() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Some fields are invalid");
        } else {
            Customer customerToUpdate = this.customerRepository.findById(id).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found to update"));

            customerToUpdate.setName(customer.getName());
            customerToUpdate.setEmail(customer.getEmail());
            customerToUpdate.setPhone(customer.getPhone());

            Date today = new Date();
            customerToUpdate.setUpdatedAt(today);

            return new ResponseEntity<>(this.customerRepository.save(customerToUpdate), HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable int id) {
        Customer customerToDelete = this.customerRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found to delete"));
        this.customerRepository.delete(customerToDelete);

        return ResponseEntity.ok(customerToDelete);
    }
}
