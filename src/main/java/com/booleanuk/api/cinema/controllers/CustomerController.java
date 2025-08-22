package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Customer;
import com.booleanuk.api.cinema.payload.response.CustomerListResponse;
import com.booleanuk.api.cinema.payload.response.CustomerResponse;
import com.booleanuk.api.cinema.payload.response.ErrorResponse;
import com.booleanuk.api.cinema.payload.response.Response;
import com.booleanuk.api.cinema.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;

@RestController
@RequestMapping("customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public ResponseEntity<CustomerListResponse> getAllCustomers() {
        CustomerListResponse customerListResponse = new CustomerListResponse();
        customerListResponse.set(this.customerRepository.findAll());
        return ResponseEntity.ok(customerListResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createCustomer(@RequestBody Customer customer) {
        CustomerResponse customerResponse = new CustomerResponse();
        try {
            customerResponse.set(this.customerRepository.save(customer));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateCustomer (@PathVariable int id, @RequestBody Customer customer) {
        Customer customerToUpdate = this.customerRepository.findById(id).orElse(null);
        if (customerToUpdate == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No customer with that ID found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        if (customer.getName() == null && customer.getEmail() == null && customer.getPhone() == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Could not update the customer, please check all fields are correct");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        if (customer.getName() != null){
            customerToUpdate.setName(customer.getName());
        }
        if(customer.getEmail() != null) {
            customerToUpdate.setEmail(customer.getEmail());
        }
        if(customer.getPhone() != null) {
            customerToUpdate.setPhone(customer.getPhone());
        }
        customerToUpdate.setUpdatedAt(OffsetDateTime.now());

        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.set(this.customerRepository.save(customerToUpdate));
        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteCustomer(@PathVariable int id) {
        Customer customerToDelete = this.customerRepository.findById(id).orElse(null);
        if (customerToDelete == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No customer with that ID found to delete");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        this.customerRepository.delete(customerToDelete);
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.set(customerToDelete);
        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }
}
