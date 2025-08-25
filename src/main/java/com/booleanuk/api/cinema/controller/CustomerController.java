package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.payload.response.CustomerListResponse;
import com.booleanuk.api.cinema.payload.response.CustomerResponse;
import com.booleanuk.api.cinema.payload.response.ErrorResponse;
import com.booleanuk.api.cinema.payload.response.Response;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.views.Views;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;

@RestController
@RequestMapping("customers")
public class CustomerController {


    @Autowired
    private CustomerRepository repository;

    @GetMapping
    public ResponseEntity<CustomerListResponse> getAll() {
        CustomerListResponse response = new CustomerListResponse(this.repository.findAll());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response<?>> PostUser(@RequestBody Customer customer) {
        CustomerResponse response = new CustomerResponse();
        try {
            response.set(this.repository.save(customer));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
//    @JsonView(Views.BasicInfo.class)
    public ResponseEntity<Response<?>> create(@PathVariable int id, @RequestBody Customer customer) {
        Customer customerToUpdate = this.repository.findById(id).orElse(null);
        if (customerToUpdate == null)
            return new ResponseEntity<>(new ErrorResponse("not found"), HttpStatus.NOT_FOUND);

        BeanUtils.copyProperties(customer, customerToUpdate, "id", "createdAt");
        try {
            customerToUpdate = this.repository.save(customerToUpdate);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Bad request"), HttpStatus.BAD_REQUEST);
        }
//        customerToUpdate.setUpdatedAt(OffsetDateTime.now());
        return new ResponseEntity<>(new CustomerResponse(customerToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> delete( @PathVariable int id) {
        Customer customerToDelete = this.repository.findById(id).orElse(null);
        if (customerToDelete == null)
            return new ResponseEntity<>(new ErrorResponse("not found"), HttpStatus.NOT_FOUND);

        this.repository.delete(customerToDelete);
        return ResponseEntity.ok(new CustomerResponse(customerToDelete));
    }
}
