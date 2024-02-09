package com.booleanuk.api.cinema.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController {

    LocalDateTime currentTime = LocalDateTime.now();


    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public List<Customer> getAll() {
        return this.customerRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable int id) {
        Customer customer = this.customerRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));

        return ResponseEntity.ok(customer);
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        customer.setCreatedAt(currentTime);
        customer.setUpdatedAt(currentTime);
        return new ResponseEntity<Customer>(this.customerRepository.save(customer), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> deleteCustomer (@PathVariable int id) {
        Customer deleted = this.customerRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        this.customerRepository.delete(deleted);
        return ResponseEntity.ok(deleted);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer (@PathVariable int id, @RequestBody Customer customer) {

        Customer customerToUpdate = this.customerRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        customerToUpdate.setName(customer.getName());
        customerToUpdate.setEmail(customer.getEmail());
        customerToUpdate.setPhone(customer.getPhone());
        customerToUpdate.setUpdatedAt(currentTime);
        return new ResponseEntity<>(this.customerRepository.save(customerToUpdate), HttpStatus.CREATED);
    }
}
