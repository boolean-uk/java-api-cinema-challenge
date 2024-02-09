package com.booleanuk.api.cinema.extension.controller;

import com.booleanuk.api.cinema.extension.model.Customer;
import com.booleanuk.api.cinema.extension.repository.CustomerRepository;
import com.booleanuk.api.cinema.extension.response.CustomResponse;
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
    private CustomerRepository customerRepository;

    @GetMapping
    public ResponseEntity<CustomResponse> getAllCustomers() {
        CustomResponse customResponse = new CustomResponse("success", customerRepository.findAll());
        return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<CustomResponse> getCustomerById(@PathVariable("id") int id) {
        Customer customer = customerRepository.findById((long) id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));

        CustomResponse customResponse = new CustomResponse("success", customer);
        return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomResponse> createCustomer(@RequestBody Customer customer) {
        checkIfCustomerIsValid(customer);
        Customer newCustomer = customerRepository.save(customer);

        CustomResponse customResponse = new CustomResponse("success", newCustomer);
        return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<CustomResponse> updateCustomer(@PathVariable("id") int id, @RequestBody Customer customer) {
        checkIfCustomerIsValid(customer);
        Customer customerToUpdate = customerRepository
                .findById((long) id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        if (customerToUpdate != null) {
            customerToUpdate.setName(customer.getName());
            customerToUpdate.setEmail(customer.getEmail());
            customerToUpdate.setPhone(customer.getPhone());
            customerRepository.save(customerToUpdate);
        }

        CustomResponse customResponse = new CustomResponse("success", customerToUpdate);
        return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public Customer deleteCustomer(@PathVariable("id") int id) {
        Customer customerToDelete = customerRepository
                .findById((long) id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        if (customerToDelete != null) {
            customerRepository.delete(customerToDelete);
        }
        return customerToDelete;
    }

    private void checkIfCustomerIsValid(Customer customer) {
        if (customer.getName() == null || customer.getName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name is required");
        } else if (customer.getEmail() == null || customer.getEmail().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is required");
        } else if (customer.getPhone() == null || customer.getPhone().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Phone number is required");
        }
    }
}
