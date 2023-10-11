package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.response.CustomerListResponse;
import com.booleanuk.api.cinema.response.CustomerResponse;
import com.booleanuk.api.cinema.response.ErrorResponse;
import com.booleanuk.api.cinema.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public ResponseEntity<CustomerListResponse> getCustomers() {
        CustomerListResponse customerListResponse = new CustomerListResponse();
        customerListResponse.set(this.customerRepository.findAll());
        return ResponseEntity.ok(customerListResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable int id) {
        Customer customer = this.customerRepository.findById(id).orElse(null);
        return ResponseEntity.ok(customer);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createCustomer(@RequestBody Customer customer) {
        CustomerResponse customerResponse = new CustomerResponse();
        if (customer.getName() == null || customer.getEmail() == null || customer.getPhone() == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        try {
            customerResponse.set(this.customerRepository.save(customer));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        Customer customerToUpdate = this.customerRepository.findById(id).orElse(null);

        if (customerToUpdate == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
            try {
                customerToUpdate = this.customerRepository.findById(id).orElse(null);
            } catch (Exception e) {
                ErrorResponse secondError = new ErrorResponse();
                secondError.set("bad request");
                return new ResponseEntity<>(secondError, HttpStatus.BAD_REQUEST);
            }
            if (customer.getName() != null) {
                customerToUpdate.setName(customer.getName());
            }
            if (customer.getEmail() != null) {
                customerToUpdate.setEmail(customer.getEmail());
            }
            if (customer.getPhone() != null) {
                customerToUpdate.setPhone(customer.getPhone());
            }

            this.customerRepository.save(customerToUpdate);

            CustomerResponse customerResponse = new CustomerResponse();
            customerResponse.set(customerToUpdate);
            return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
        }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteCustomer(@PathVariable int id) {
        Customer customerToDelete = this.customerRepository.findById(id).orElse(null);
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
