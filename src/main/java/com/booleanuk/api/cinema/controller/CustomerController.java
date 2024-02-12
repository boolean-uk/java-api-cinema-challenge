package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.dto.*;
import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.response.ApiSuccessResponse;
import com.booleanuk.api.cinema.response.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController {
    @Autowired
    CustomerRepository repository;

    @GetMapping
    public ResponseEntity<ApiSuccessResponse<List<CustomerDto>>> getAllCustomers() {
        return ResponseEntity.ok(new ApiSuccessResponse<>(this.repository.findAllProjectedBy()));
    }

    @PostMapping
    public ResponseEntity<ApiSuccessResponse<CustomerDto>> createCustomer(@RequestBody Customer customer) {
        if (customer.getEmail() == null || customer.getName() == null || customer.getPhone() == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "bad request");
        }
        Customer createdCustomer = this.repository.save(customer);
        createdCustomer.setTickets(new ArrayList<>());
        return new ResponseEntity<>(new ApiSuccessResponse<>(this.translateToDto(createdCustomer)), HttpStatus.CREATED);
    }

    public CustomerDto translateToDto(Customer customer) {
        return new CustomerDto(customer.getId(), customer.getName(), customer.getEmail(), customer.getPhone(), customer.getCreatedAt(), customer.getUpdatedAt());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiSuccessResponse<CustomerDto>> deleteCustomer(@PathVariable int id) {
        Customer customer = this.repository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "not found"));
        try {
            this.repository.delete(customer);
        } catch (Exception e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Customer still references a ticket");
        }
        customer.setTickets(new ArrayList<>());
        return ResponseEntity.ok(new ApiSuccessResponse<>(this.translateToDto(customer)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiSuccessResponse<CustomerDto>> updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        if (customer.getEmail() == null || customer.getName() == null || customer.getPhone() == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "bad request");
        }
        Customer updateCustomer = this.repository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "not found"));
        updateCustomer.setName(customer.getName());
        updateCustomer.setEmail(customer.getEmail());
        updateCustomer.setPhone(customer.getPhone());

        return new ResponseEntity<>(new ApiSuccessResponse<>(this.translateToDto(this.repository.save(updateCustomer))), HttpStatus.CREATED);
    }
}
