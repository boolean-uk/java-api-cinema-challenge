package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.responses.CustomerListResponse;
import com.booleanuk.api.cinema.responses.CustomerResponse;
import com.booleanuk.api.cinema.responses.ErrorResponse;
import com.booleanuk.api.cinema.responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("customers")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;


    @GetMapping
    public ResponseEntity<CustomerListResponse> getAllCustomers(){
        CustomerListResponse customerListResponse = new CustomerListResponse();
        customerListResponse.set(this.customerRepository.findAll());
        return ResponseEntity.ok(customerListResponse);
    }



    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Response<?>> create(@RequestBody Customer request) {
        CustomerResponse customerResponse = new CustomerResponse();
        try{
            customerResponse.set(this.customerRepository.save(request));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }



    @PutMapping("{id}")
    public ResponseEntity<Response<?>> updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        Customer customerToUpdate = null;

        try {
            customerToUpdate = this.customerRepository.findById(id).orElse(null);
            if(customerToUpdate == null){
                ErrorResponse error = new ErrorResponse();
                error.set("not found");
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }
            customerToUpdate.setName(customer.getName());
            customerToUpdate.setEmail(customer.getEmail());
            customerToUpdate.setPhone(customer.getPhone());
            customerToUpdate.setUpdatedAt(LocalDate.now());
            customerToUpdate = this.customerRepository.save(customerToUpdate);

        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.set(customerToUpdate);

        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }



    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> deleteCustomer(@PathVariable int id) {
        Customer customerToDelete = this.customerRepository.findById(id).orElse(null);

        if(customerToDelete == null){
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
