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


import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.Optional;

@RestController
@RequestMapping("customers")
public class CustomerController {
    private LocalDateTime today;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public ResponseEntity<CustomerListResponse> getAll(){
        CustomerListResponse customerListResponse = new CustomerListResponse();
        customerListResponse.set(this.customerRepository.findAll());
        return ResponseEntity.ok(customerListResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getCustomerById(@PathVariable int id){
        Customer findCustomer = findOne(id);
        if (findCustomer == null){
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
        }

        CustomerResponse response = new CustomerResponse();
        response.set(findCustomer);
        return ResponseEntity.ok(response);

    }

    @PostMapping
    public ResponseEntity<Response<?>> addCustomer(@RequestBody Customer customer){
        today = LocalDateTime.now();

        if (customer.getName() == null ||
                customer.getPhone() == null ||
                customer.getEmail() == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("bad request");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        customer.setCreatedAt(String.valueOf(today));
        customer.setUpdatedAt(String.valueOf(today));
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.set(this.customerRepository.save(customer));


        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateCustomer(@PathVariable int id, @RequestBody Customer customer){
        today = LocalDateTime.now();

        Customer updateCustomer = findOne(id);

        if (updateCustomer == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        if (customer.getPhone() != null){
            try {
                Integer.parseInt(customer.getPhone());
            } catch (NumberFormatException e){
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.set("bad request");
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            }
        }

        Optional.ofNullable(customer.getName())
                .ifPresent(updateCustomer::setName);

        Optional.ofNullable(customer.getEmail())
                .ifPresent(updateCustomer::setEmail);

        Optional.ofNullable(customer.getPhone())
                        .ifPresent(updateCustomer::setPhone);

        updateCustomer.setUpdatedAt(String.valueOf(today));

        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.set(this.customerRepository.save(updateCustomer));
        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteCustomer(@PathVariable int id){

        Customer deleteCustomer = findOne(id);
        if (deleteCustomer == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        CustomerResponse customerResponse = new CustomerResponse();
        this.customerRepository.delete(deleteCustomer);
        deleteCustomer.setTickets(new ArrayList<>());
        customerResponse.set(deleteCustomer);
        return ResponseEntity.ok(customerResponse);
    }

    private Customer findOne(int id){
        return this.customerRepository.findById(id)
                .orElse(null);
    }

}
