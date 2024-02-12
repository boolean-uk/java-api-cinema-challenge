package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.response.CustomResponse;
import com.booleanuk.api.cinema.response.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController {


    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public ResponseEntity<CustomResponse> getAllCustomers() {
        if (customerRepository.count() < 1) {
            CustomResponse errResponse = new CustomResponse("Error", new Error("No data found"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errResponse);
        }

        return ResponseEntity.ok(new CustomResponse("success", this.customerRepository.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getCustomerByID(@PathVariable int id) {
        if (!customerRepository.existsById(id)) {
            CustomResponse errResponse = new CustomResponse("Error", new Error("Id is not in database!"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errResponse);
        }

        Customer customer = this.customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found!"));
        return ResponseEntity.ok(new CustomResponse("Success", customer));
    }

    @PostMapping
    public ResponseEntity<CustomResponse> createCustomer(@RequestBody Customer customer) {
        if (customer.getName() == null || customer.getEmail() == null || customer.getPhone() == null) {
            CustomResponse errResponse = new CustomResponse("Error", new Error("Check if all fields are correct!"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errResponse);
        }
        return ResponseEntity.ok(new CustomResponse("success", this.customerRepository.save(customer)));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse> deleteCustomerByID(@PathVariable int id) {

        if (!customerRepository.existsById(id)) {
            CustomResponse errResponse = new CustomResponse("Error", new Error("Id is not in database!"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errResponse);
        }

        Customer customer = this.customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found!"));
        this.customerRepository.delete(customer);
        customer.setTickets(new ArrayList<Ticket>());
        return ResponseEntity.ok(new CustomResponse("Success", customer));


    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse> updateCustomer(@PathVariable int id, @RequestBody Customer customer) {

        if (customer.getName() == null || customer.getEmail() == null || customer.getPhone() == null) {
            CustomResponse errResponse = new CustomResponse("Error", new Error("Check if all fields are correct!"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errResponse);
        }

        Customer customerToUpdate = this.customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found!"));
        customerToUpdate.setName(customer.getName());
        customerToUpdate.setEmail(customer.getEmail());
        customerToUpdate.setPhone(customer.getPhone());
        customerToUpdate.setCreatedAt(customer.getCreatedAt());
        customerToUpdate.setUpdatedAt(customer.getUpdatedAt());

        return ResponseEntity.ok(new CustomResponse("Success", this.customerRepository.save(customerToUpdate)));
    }

}
