package com.booleanuk.api.cinema.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        validateCustomerOrThrowException(customer);

        Customer newCustomer = this.customerRepository.save(customer);

        return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(this.customerRepository.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        //validateCustomerOrThrowException(customer);

        if(customer.getName() == null && customer.getEmail() == null && customer.getPhone() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update customer, all provided fields are null.");
        }

        Customer customerToBeUpdated = findCustomerOrThrowException(id);

        // If any field is not provided, the original value should not be changed. Any combination of fields can be updated.
        if(customer.getName() != null) {
            customerToBeUpdated.setName(customer.getName());
        }
        if(customer.getEmail() != null) {
            customerToBeUpdated.setEmail(customer.getEmail());
        }
        if(customer.getPhone() != null) {
            customerToBeUpdated.setPhone(customer.getPhone());
        }

        this.customerRepository.save(customerToBeUpdated);

        return ResponseEntity.ok(customerToBeUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable int id) {
        Customer customerToBeDeleted = findCustomerOrThrowException(id);

        this.customerRepository.deleteById(id);

        return ResponseEntity.ok(customerToBeDeleted);
    }

    private void validateCustomerOrThrowException(Customer customer) {
        if(customer.getName() == null || customer.getEmail() == null || customer.getPhone() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create a new customer, please check all fields are correct.");
        }
    }

    private Customer findCustomerOrThrowException(int id) {
        return this.customerRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No customer with that id found."));
    }
}
