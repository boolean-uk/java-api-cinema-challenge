package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import com.booleanuk.api.cinema.utility.Responses.CustomerListResponse;
import com.booleanuk.api.cinema.utility.Responses.CustomerResponse;
import com.booleanuk.api.cinema.utility.DataResponse;
import com.booleanuk.api.cinema.utility.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerRepository customers;
    @Autowired
    private TicketRepository tickets;

    @PostMapping
    public ResponseEntity<DataResponse<?>> createCustomer(@RequestBody Customer customer) {
        Customer customerToCreate;
        try {
            customer.setCreatedAt(ZonedDateTime.now());
            customer.setUpdatedAt(ZonedDateTime.now());
            customerToCreate = this.customers.save(customer);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Could not create a new customer, please check all fields are correct.");
            return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
        }
        CustomerResponse response = new CustomerResponse();
        response.set(customerToCreate);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<CustomerListResponse> getAllCustomers() {
        CustomerListResponse response = new CustomerListResponse();
        response.set(this.customers.findAll(Sort.by(Sort.Direction.ASC,"customerId")));
        return ResponseEntity.ok(response);
    }

    private Customer getCustomer(int id) {
        return this.customers.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataResponse<?>> updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        Customer customerToUpdate = this.getCustomer(id);
        if (customerToUpdate == null){
            ErrorResponse error = new ErrorResponse();
            error.set("No customer with that id found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        try {
            customerToUpdate.setName(customer.getName());
            customerToUpdate.setEmail(customer.getEmail());
            customerToUpdate.setPhone(customer.getPhone());
            customerToUpdate.setUpdatedAt(ZonedDateTime.now());
            customerToUpdate = this.customers.save(customerToUpdate);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Could not update the specified customer, please check all fields are correct.");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        CustomerResponse response = new CustomerResponse();
        response.set(customerToUpdate);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DataResponse<?>> deleteCustomer(@PathVariable int id) {
        Customer customerToDelete = this.getCustomer(id);
        if (customerToDelete == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No customer with that id found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        List<Ticket> ticketsToDelete = this.tickets.findByCustomer(customerToDelete);
        if (ticketsToDelete != null){
            this.tickets.deleteAll(ticketsToDelete);
        }
        this.customers.delete(customerToDelete);
        CustomerResponse response = new CustomerResponse();
        response.set(customerToDelete);
        return ResponseEntity.ok(response);
    }
}
