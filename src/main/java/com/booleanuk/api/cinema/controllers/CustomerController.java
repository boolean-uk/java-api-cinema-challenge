package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Customer;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.response.CustomerListResponse;
import com.booleanuk.api.cinema.response.CustomerResponse;
import com.booleanuk.api.cinema.response.ErrorResponse;
import com.booleanuk.api.cinema.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController {

    private CustomerListResponse customerListResponse = new CustomerListResponse();
    private CustomerResponse customerResponse = new CustomerResponse();

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public ResponseEntity<Response<?>> getAllCustomers () {
        List<Customer> customers = customerRepository.findAll();
        this.customerListResponse.set(customers);

        return ResponseEntity.ok(customerListResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createCustomer(@RequestBody Customer customer){

        if (customer.getName() == null || customer.getEmail() == null || customer.getPhone() == null){
            ErrorResponse error = new ErrorResponse();
            error.set("Could not create a new customer, please check all fields are correct");

            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());
        this.customerResponse.set(customer);
        this.customerRepository.save(customer);

        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<?>> updateCustomer(@PathVariable int id, @RequestBody Customer customer){

        if (customer.getName() == null || customer.getEmail() == null || customer.getPhone() == null){
            ErrorResponse error = new ErrorResponse();
            error.set("Could not create a new customer, please check all fields are correct");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        Customer customerToUpdate = customerRepository.findById(id).orElse(null);
        if(customerToUpdate == null){
                ErrorResponse error = new ErrorResponse();
                error.set("No customer with that ID found");
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        customerToUpdate.setName(customer.getName());
        customerToUpdate.setEmail(customer.getEmail());
        customerToUpdate.setPhone(customer.getPhone());
        customerToUpdate.setUpdatedAt(LocalDateTime.now());
        this.customerResponse.set(customerToUpdate);
        this.customerRepository.save(customerToUpdate);

        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> deleteCustomer(@PathVariable int id){

        Customer customerToDelete = this.customerRepository.findById(id).orElse(null);
        if (customerToDelete == null){
            ErrorResponse error = new ErrorResponse();
            error.set("No customer with that ID found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        this.customerResponse.set(customerToDelete);
        this.customerRepository.delete(customerToDelete);

        return ResponseEntity.ok(customerResponse);
    }
}
