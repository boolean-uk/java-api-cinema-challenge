package com.booleanuk.api.cinema.customers;

import com.booleanuk.api.cinema.responses.CustomerListResponse;
import com.booleanuk.api.cinema.responses.CustomerResponse;
import com.booleanuk.api.cinema.responses.ErrorResponse;
import com.booleanuk.api.cinema.responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("customers")

public class CustomerController {

    @Autowired
    private final CustomerRepository repository;

    public CustomerController(CustomerRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<CustomerListResponse> getAll() {
        CustomerListResponse customerListResponse = new CustomerListResponse();
        customerListResponse.set(this.repository.findAll());
        return ResponseEntity.ok(customerListResponse);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Response<?>> create(@RequestBody Customer customer) {
        CustomerResponse customerResponse = new CustomerResponse();
        try {
            customerResponse.set(this.repository.save(customer));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request - cannot create customer");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<?>> updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        Customer customerToUpdate = null;
        try {
            customerToUpdate = this.repository.findById(id).orElse(null);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request - could not successfully find customer");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        if (customerToUpdate == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Customer not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        customerToUpdate.setName(customer.getName());
        customerToUpdate.setEmail(customer.getEmail());
        customerToUpdate.setPhone(customer.getPhone());
        customerToUpdate.setUpdatedAt(LocalDate.now());
        customerToUpdate= this.repository.save(customerToUpdate);
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.set(customerToUpdate);
        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> deleteACustomer(@PathVariable int id) {
        Customer customerToDelete = this.repository.findById(id).orElse(null);
        if (customerToDelete == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Customer not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        this.repository.delete(customerToDelete);
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.set(customerToDelete);
        return ResponseEntity.ok(customerResponse);
    }
}
