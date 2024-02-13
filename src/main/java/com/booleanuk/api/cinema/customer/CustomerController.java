package com.booleanuk.api.cinema.customer;

import com.booleanuk.api.cinema.response.*;
import com.booleanuk.api.cinema.response.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("customers")
public class CustomerController {

    LocalDateTime currentTime = LocalDateTime.now();


    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public ResponseEntity<Response> getAll() {
        return new ResponseEntity<>(new CustomerListResponse(this.customerRepository.findAll()), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Response> getCustomer(@PathVariable int id) {
        Customer customer = this.customerRepository
                .findById(id)
                .orElse(null);
        if (customer == null) {
            return new ResponseEntity<>(new ErrorResponse(new Error("Customer not found")), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new CustomerResponse(customer), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Response> createCustomer(@RequestBody Customer customer) {

        if(customer.getEmail().isEmpty() || customer.getPhone().isEmpty() || customer.getName().isEmpty()){
            return new ResponseEntity<>(new ErrorResponse(new Error("Bad Request")), HttpStatus.BAD_REQUEST);
        }
        customer.setCreatedAt(currentTime);
        customer.setUpdatedAt(currentTime);
        this.customerRepository.save(customer);
        return new ResponseEntity<>(new CustomerResponse(customer), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteCustomer (@PathVariable int id) {
        Customer deleted = this.customerRepository
                .findById(id)
                .orElse(null);

        if (deleted == null) {
            return new ResponseEntity<>(new ErrorResponse(new Error("Customer not found")), HttpStatus.NOT_FOUND);
        }
        this.customerRepository.delete(deleted);
        return new ResponseEntity<>(new CustomerResponse(deleted), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateCustomer (@PathVariable int id, @RequestBody Customer customer) {

        Customer customerToUpdate = this.customerRepository
                .findById(id)
                .orElse(null);
        if(customerToUpdate == null) {
            return new ResponseEntity<>(new ErrorResponse(new Error("Customer not found")), HttpStatus.NOT_FOUND);
        }

        if(customer.getName().isEmpty() || customer.getEmail().isEmpty() || customer.getPhone().isEmpty()){
            return new ResponseEntity<>(new ErrorResponse(new Error("Bad Request")), HttpStatus.BAD_REQUEST);
        }

        customerToUpdate.setName(customer.getName());
        customerToUpdate.setEmail(customer.getEmail());
        customerToUpdate.setPhone(customer.getPhone());
        customerToUpdate.setUpdatedAt(currentTime);
        this.customerRepository.save(customerToUpdate);
        return new ResponseEntity<>(new CustomerResponse(customerToUpdate), HttpStatus.OK);
    }
}
