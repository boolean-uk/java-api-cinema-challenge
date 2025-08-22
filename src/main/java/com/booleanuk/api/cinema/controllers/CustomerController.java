package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.*;
import com.booleanuk.api.cinema.payload.response.*;
import com.booleanuk.api.cinema.repository.RoleRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.UserRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("customers")
public class CustomerController {
    @Autowired
    private UserRepository customerRepository;
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping
    public ResponseEntity<CustomerListResponse> getAllUsers() {
        CustomerListResponse customerListResponse = new CustomerListResponse();
        customerListResponse.set(this.customerRepository.findAll());
        return ResponseEntity.ok(customerListResponse);
    }

    public record PostUser(String name, String email, String phone) {}

    @PostMapping
    public ResponseEntity<Response<?>> createUser(@RequestBody PostUser request) {
        CustomerResponse customerResponse = new CustomerResponse();
        Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found"));
        User customer = new User(request.name(), request.email(), request.phone(), "password", userRole);
        try {
            customerResponse.set(this.customerRepository.save(customer));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getUserById(@PathVariable int id) {
        User customer = this.customerRepository.findById(id).orElse(null);
        if (customer == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.set(customer);
        return ResponseEntity.ok(customerResponse);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateUser(@PathVariable int id, @RequestBody PostUser customer) {
        User customerToUpdate = this.customerRepository.findById(id).orElse(null);
        if (customerToUpdate == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        customerToUpdate.setUsername(customer.name());
        customerToUpdate.setEmail(customer.email());
        customerToUpdate.setPhone(customer.phone());
        customerToUpdate.setUpdatedAt(OffsetDateTime.now());

        try {
            customerToUpdate = this.customerRepository.save(customerToUpdate);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.set(customerToUpdate);
        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteUser(@PathVariable int id) {
        User customerToDelete = this.customerRepository.findById(id).orElse(null);
        if (customerToDelete == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        this.customerRepository.delete(customerToDelete);
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.set(customerToDelete);
        return ResponseEntity.ok(customerResponse);
    }
}
