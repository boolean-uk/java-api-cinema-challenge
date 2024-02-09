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
        validateCustomerOrThrowException(customer);

        Customer customerToBeUpdated = findCustomerOrThrowException(id);

        customerToBeUpdated.setName(customer.getName());
        customerToBeUpdated.setEmail(customer.getEmail());
        customerToBeUpdated.setPhone(customer.getPhone());

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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create/update new customer, please check all fields are correct.");
        }
    }

    private Customer findCustomerOrThrowException(int id) {
        return this.customerRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No customer with that id found."));
    }
}
