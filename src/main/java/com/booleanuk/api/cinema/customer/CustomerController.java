package com.booleanuk.api.cinema.customer;

import com.booleanuk.api.cinema.response.CustomerResponse;
import com.booleanuk.api.cinema.response.CustomerResponseList;
import com.booleanuk.api.cinema.response.ErrorResponse;
import com.booleanuk.api.cinema.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping
    public ResponseEntity<Response<?>> createCustomer(@RequestBody Customer customer) {
        CustomerResponse response = new CustomerResponse();
        try {
            customer.setCreated_at(String.valueOf(LocalDateTime.now()));
            response.set(this.customerRepository.save(customer));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CustomerResponseList> getAllCustomers() {
        CustomerResponseList response = new CustomerResponseList();
        response.set(this.customerRepository.findAll());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        Customer updatedCustomer = null;
        CustomerResponse response = new CustomerResponse();

        try {
            updatedCustomer = this.customerRepository.findById(id).orElse(null);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        if (updatedCustomer == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        updatedCustomer.setName(customer.getName());
        updatedCustomer.setEmail(customer.getEmail());
        updatedCustomer.setPhone(customer.getPhone());
        updatedCustomer.setUpdated_at(String.valueOf(LocalDateTime.now()));
        response.set(this.customerRepository.save(updatedCustomer));

        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteCustomer(@PathVariable int id) {
        Customer deletedCustomer = this.customerRepository.findById(id).orElse(null);
        CustomerResponse response = new CustomerResponse();

        if (deletedCustomer == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        this.customerRepository.delete(deletedCustomer);
        response.set(deletedCustomer);
        return ResponseEntity.ok(response);
    }
}
