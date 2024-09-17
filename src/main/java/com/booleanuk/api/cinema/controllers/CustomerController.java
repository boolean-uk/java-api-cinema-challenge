package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Customer;
import com.booleanuk.api.cinema.repositories.CustomerRepository;
import com.booleanuk.api.cinema.responses.CustomerListResponse;
import com.booleanuk.api.cinema.responses.CustomerResponse;
import com.booleanuk.api.cinema.responses.ErrorResponse;
import com.booleanuk.api.cinema.responses.Response;
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
    CustomerRepository repository;

    @PostMapping
    public ResponseEntity<Response<?>> create(@RequestBody Customer customer) {
        CustomerResponse response = new CustomerResponse();
        response.set(customer);
        try {
            this.repository.save(customer);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Could not create a new customer, please check all fields are correct.");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Response<?>> getAll() {
        CustomerListResponse response = new CustomerListResponse();
        List<Customer> customers = this.repository.findAll();
        if (customers.isEmpty()) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No customers found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        response.set(this.repository.findAll());
        return ResponseEntity.ok(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<?>> update(
            @PathVariable int id,
            @RequestBody Customer customer)
    {
        CustomerResponse response = new CustomerResponse();
        try {
            Customer originalCustomer = this.getObjectById(id);
            customer.setId(id);
            customer.setCreatedAt(originalCustomer.getCreatedAt());
            this.repository.save(customer);

            // TODO: inefficient to call getObjectById again, refactor?
            // Get the updated value, added on table update
            customer.setUpdatedAt(getObjectById(id).getUpdatedAt());
            response.set(customer);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set(e.getMessage());

            // TODO: Duplicate code for HttpStatus.NOT_FOUND, both in exception and again here. Refactor?
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> delete(@PathVariable int id) {
        CustomerResponse response = new CustomerResponse();
        try {
            Customer customer = getObjectById(id);
            this.repository.deleteById(id);
            response.set(customer);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set(e.getMessage());

            // TODO: Duplicate code for HttpStatus.NOT_FOUND, both in exception and again here. Refactor?
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(response);
    }

    /**
     * Get object by id.
     * Can be used to check for valid id (throws exception if id doesn't exist).
     * @param id .
     * @return Customer
     */
    private Customer getObjectById(int id) {
        Customer customer = this.repository
                .findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "No customer with id #"+id+" found."
                        )
                );
        return customer;
    }
}
