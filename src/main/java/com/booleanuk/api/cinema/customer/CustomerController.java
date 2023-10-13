package com.booleanuk.api.cinema.customer;

import com.booleanuk.api.cinema.response.CustomerListResponse;
import com.booleanuk.api.cinema.response.CustomerResponse;
import com.booleanuk.api.cinema.response.ErrorResponse;
import com.booleanuk.api.cinema.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public ResponseEntity<CustomerListResponse> getAllCustomers() {
        CustomerListResponse customerListResponse = new CustomerListResponse();
        customerListResponse.set(this.customerRepository.findAll());
        return ResponseEntity.ok(customerListResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createCustomer(@RequestBody Customer customer) {
        CustomerResponse customerResponse = new CustomerResponse();

        if (customer.getName() == null || customer.getEmail() == null || customer.getPhone() == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Some fields are invalid");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

        } else {
            Date today = new Date();
            customer.setCreatedAt(today);
            customer.setUpdatedAt(today);

            customerResponse.set(this.customerRepository.save(customer));
        }
        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        CustomerResponse customerResponse = new CustomerResponse();

        if (customer.getName() == null || customer.getEmail() == null || customer.getPhone() == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Some fields are invalid");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

        } else {
            Customer customerToUpdate = this.customerRepository.findById(id).orElse(null);

            if (customerToUpdate == null) {
                ErrorResponse error = new ErrorResponse();
                error.set("Not found to update");
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

            } else {
                customerToUpdate.setName(customer.getName());
                customerToUpdate.setEmail(customer.getEmail());
                customerToUpdate.setPhone(customer.getPhone());

                Date today = new Date();
                customerToUpdate.setUpdatedAt(today);
                customerResponse.set(this.customerRepository.save(customerToUpdate));
            }
            return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteCustomer(@PathVariable int id) {
        CustomerResponse customerResponse = new CustomerResponse();
        Customer customerToDelete = this.customerRepository.findById(id).orElse(null);

        if (customerToDelete == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Not found to delete");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        this.customerRepository.delete(customerToDelete);
        customerResponse.set(customerToDelete);

        return ResponseEntity.ok(customerResponse);
    }
}
