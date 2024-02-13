package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.response.ErrorResponse;
import com.booleanuk.api.cinema.response.Response;
import com.booleanuk.api.cinema.response.SuccessResponse;
import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping
    public ResponseEntity<Response> createCustomer(@RequestBody Customer customer) {
        // 400 Bad request if not all fields are present
        if (customer.getName() == null || customer.getEmail() == null || customer.getPhone() == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Could not create a new customer, please check all fields are correct.");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        customer.setCreatedAt(ZonedDateTime.now());
        customer.setUpdatedAt(ZonedDateTime.now());

        // Response with the created customer
        SuccessResponse response = new SuccessResponse();
        response.set(this.customerRepository.save(customer));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Response> getAllCustomers() {
        List<Customer> customers = this.customerRepository.findAll();
        // Response with the list of customers
        SuccessResponse response = new SuccessResponse();
        response.set(customers);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        Customer customerToUpdate = this.getCustomerById(id);
        // 404 Not found if no customer with given id exists
        if (customerToUpdate == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No customer with that ID found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        // 400 Bad request if no fields are present in the put request
        if (customer.getName() == null && customer.getEmail() == null && customer.getPhone() == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Could not update the customer, please check all fields are correct.");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
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

        // Response with the updated customer
        SuccessResponse response = new SuccessResponse();
        response.set(this.customerRepository.save(customerToUpdate));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteCustomer(@PathVariable int id) {
        Customer customerToDelete = this.getCustomerById(id);
        // 404 Not found if no customer with the given id exists
        if (customerToDelete == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No customer with that ID found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        // Delete customer
        this.customerRepository.delete(customerToDelete);

        // Response with the deleted customer
        SuccessResponse response = new SuccessResponse();
        response.set(customerToDelete);
        return ResponseEntity.ok(response);
    }

    // Method used in updateCustomer() and deleteCustomer() to find a customer by the id
    private Customer getCustomerById(int id) {
        return this.customerRepository.findById(id).orElse(null);
    }

}
