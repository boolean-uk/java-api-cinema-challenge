package com.booleanuk.api.cinema.customers;

import com.booleanuk.api.cinema.responses.CustomerListResponse;
import com.booleanuk.api.cinema.responses.CustomerResponse;
import com.booleanuk.api.cinema.responses.ErrorResponse;
import com.booleanuk.api.cinema.responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("customers")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    private ErrorResponse errorResponse = new ErrorResponse();
    private CustomerResponse customerResponse = new CustomerResponse();
    private CustomerListResponse customerListResponse = new CustomerListResponse();

    @GetMapping
    public ResponseEntity<Response<?>> getAllCustomers() {
        this.customerListResponse.set(this.customerRepository.findAll());
        return ResponseEntity.ok(customerListResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createCustomer(@RequestBody Customer customer) {
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());
        if (customer.getName() == null || customer.getEmail() == null || customer.getPhone() == null) {
            this.errorResponse.set("Could not create a new customer, please check all fields are correct");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        this.customerResponse.set(this.customerRepository.save(customer));
        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<?>> getCustomerById(@PathVariable int id) {
        Customer customer = this.customerRepository.findById(id).orElse(null);
        if (customer == null) {
            this.errorResponse.set("No customer with that id found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        this.customerResponse.set(customer);
        return ResponseEntity.ok(customerResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<?>> updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        Customer customerToUpdate = this.customerRepository.findById(id).orElse(null);
        if (customerToUpdate == null) {
            this.errorResponse.set("No customer with that id found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        if (customer.getName() == null || customer.getEmail() == null || customer.getPhone() == null) {
            this.errorResponse.set("Could not update the specified customer, please check all fields are correct");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        customerToUpdate.setName(customer.getName());
        customerToUpdate.setEmail(customer.getEmail());
        customerToUpdate.setPhone(customer.getPhone());
        customerToUpdate.setUpdatedAt(LocalDateTime.now());
        this.customerResponse.set(this.customerRepository.save(customerToUpdate));
        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> deleteCustomerById(@PathVariable int id) {
        Customer customerToDelete = this.customerRepository.findById(id).orElse(null);
        if (customerToDelete == null) {
            this.errorResponse.set("No customer with that id found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        this.customerRepository.delete(customerToDelete);
        this.customerResponse.set(customerToDelete);
        return ResponseEntity.ok(customerResponse);
    }

}