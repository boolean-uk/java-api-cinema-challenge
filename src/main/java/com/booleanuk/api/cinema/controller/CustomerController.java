package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.response.customer.CustomerListResponse;
import com.booleanuk.api.cinema.response.customer.CustomerResponse;
import com.booleanuk.api.cinema.response.ErrorResponse;
import com.booleanuk.api.cinema.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public ResponseEntity<CustomerListResponse> getAllCustomers() {
        List<Customer> customers = this.customerRepository.findAll();
        CustomerListResponse customerListResponse = new CustomerListResponse();
        customerListResponse.set(customers);
        return ResponseEntity.ok(customerListResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getCustomerById(@PathVariable int id) {
        Customer returnCustomer = this.customerRepository.findById(id).orElse(null);
        if (returnCustomer == null ) {
            ErrorResponse error = new ErrorResponse();
            error.set("No customers matching that id were found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.set(returnCustomer);
        return ResponseEntity.ok(customerResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createCustomer(@RequestBody Customer customer) {
        if (customer.getName() == null || customer.getEmail() == null || customer.getPhone() == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Could not create the customer, please check all required fields");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        Customer createdCustomer = this.customerRepository.save(customer);
        createdCustomer.setTickets(new ArrayList<>());

        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.set(createdCustomer);
        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        if (customer.getName() == null || customer.getEmail() == null || customer.getPhone() == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Could not update the customer's details, please check all required fields");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        Customer customerToUpdate = this.customerRepository.findById(id).orElse(null);
        if(customerToUpdate == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No customers matching that id were found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        customerToUpdate.setName(customer.getName());
        customerToUpdate.setEmail(customer.getEmail());
        customerToUpdate.setPhone(customer.getPhone());
        customerToUpdate.setCreatedAt(customer.getCreatedAt());
        customerToUpdate.setUpdatedAt(customer.getUpdatedAt());
        customerToUpdate.setTickets(new ArrayList<>());
        Customer alteredCustomer = this.customerRepository.save(customerToUpdate);

        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.set(alteredCustomer);
        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteCustomer(@PathVariable int id) {
        Customer customerToDelete = this.customerRepository.findById(id).orElse(null);

        if (customerToDelete == null ) {
            ErrorResponse error = new ErrorResponse();
            error.set("No customers matching that id were found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        this.customerRepository.delete(customerToDelete);
        customerToDelete.setTickets(new ArrayList<Ticket>());

        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.set(customerToDelete);
        return ResponseEntity.ok(customerResponse);
    }
}
