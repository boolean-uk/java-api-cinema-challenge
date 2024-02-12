package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.response.BadRequestResponse;
import com.booleanuk.api.cinema.response.NotFoundResponse;
import com.booleanuk.api.cinema.response.Response;
import com.booleanuk.api.cinema.response.SuccessResponse;
import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("customers")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping
    public ResponseEntity<Response> createCustomer(@RequestBody Customer customer) {
        // 400 Bad request if not all fields are present
        if (customer.getName() == null || customer.getEmail() == null || customer.getPhone() == null) {
            return new ResponseEntity<>(new BadRequestResponse(), HttpStatus.BAD_REQUEST);
        }
        customer.setCreatedAt(ZonedDateTime.now());
        customer.setUpdatedAt(ZonedDateTime.now());
        return new ResponseEntity<>(new SuccessResponse(this.customerRepository.save(customer)), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Response> getAllCustomers() {
        return new ResponseEntity<>(new SuccessResponse(this.customerRepository.findAll()), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        Customer customerToUpdate = this.findCustomerById(id);
        // 404 Not found if no customer with given id exists
        if (customerToUpdate == null) {
            return new ResponseEntity<>(new NotFoundResponse(), HttpStatus.NOT_FOUND);
        }
        // 400 Bad request if no fields are present in the put request
        if (customer.getName() == null && customer.getEmail() == null && customer.getPhone() == null) {
            return new ResponseEntity<>(new BadRequestResponse(), HttpStatus.BAD_REQUEST);
        }
        // Update field only if present
        if (customer.getName() != null) {
            customerToUpdate.setName(customer.getName());
        }
        if (customer.getEmail() != null) {
            customerToUpdate.setEmail(customer.getEmail());
        }
        if (customer.getPhone() != null) {
            customerToUpdate.setPhone(customer.getPhone());
        }
        // Update updatedAt
        customerToUpdate.setUpdatedAt(ZonedDateTime.now());
        return new ResponseEntity<>(new SuccessResponse(this.customerRepository.save(customerToUpdate)), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteCustomer(@PathVariable int id) {
        Customer customerToDelete = this.findCustomerById(id);
        // 404 Not found if no customer with the given id exists
        if (customerToDelete == null) {
            return new ResponseEntity<>(new NotFoundResponse(), HttpStatus.NOT_FOUND);
        }
        this.customerRepository.delete(customerToDelete);
        return ResponseEntity.ok(new SuccessResponse(customerToDelete));
    }

    // Method used in updateCustomer() and deleteCustomer() to find a customer by the id
    private Customer findCustomerById(int id) {
        return this.customerRepository.findById(id).orElse(null);
    }

}
