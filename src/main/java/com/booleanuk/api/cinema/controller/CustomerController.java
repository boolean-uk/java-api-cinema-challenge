package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @PostMapping
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) throws ResponseStatusException {
        try {
            return new ResponseEntity<>(this.customerRepository.save(customer), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "An error occurred when creating a customer: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return new ResponseEntity<>(this.customerRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable (name = "id") int id) throws ResponseStatusException {
            return ResponseEntity.ok(findCustomerById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable (name = "id") int id, @RequestBody Customer customer) throws ResponseStatusException {
        Customer customerToUpdate = findCustomerById(id);

        try {
            customerToUpdate.setName(customer.getName());
            customerToUpdate.setPhone(customer.getPhone());
            customerToUpdate.setEmail(customer.getEmail());
            customerToUpdate.setUpdatedAt(LocalDateTime.now());

            return new ResponseEntity<>(this.customerRepository.save(customerToUpdate), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "An error occurred when attempting to update customer: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable (name = "id") int id){
        Customer customerToDelete = findCustomerById(id);
        try {
            this.customerRepository.delete(customerToDelete);
            return ResponseEntity.ok(customerToDelete);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "An error occurred when attempting to delete customer: " + e.getMessage());
        }
    }


    private Customer findCustomerById(int id){
        return this.customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer with provided ID was not found"));
    }

}
