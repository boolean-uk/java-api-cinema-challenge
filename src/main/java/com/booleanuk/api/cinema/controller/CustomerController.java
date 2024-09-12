package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.repository.CustomerRepository;

import java.time.LocalDateTime;

import com.booleanuk.api.cinema.responses.CustomerListResponse;
import com.booleanuk.api.cinema.responses.CustomerResponse;
import com.booleanuk.api.cinema.responses.ErrorResponse;
import com.booleanuk.api.cinema.responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customers;

    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody Customer toAdd) {
        toAdd.setCreatedAt(LocalDateTime.now());
        toAdd.setUpdatedAt(LocalDateTime.now());
        return new ResponseEntity<>(this.customers.save(toAdd), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<CustomerListResponse> getAll() {
        CustomerListResponse customerListResponse = new CustomerListResponse();
        customerListResponse.set(this.customers.findAll());

        return new ResponseEntity<>(customerListResponse, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<?>> getOne(@PathVariable int id) {
        Customer toReturn = this.customers.findById(id)
                .orElse(
                        null
                );

        if (toReturn == null) {
            return new ResponseEntity<>(new ErrorResponse("not found"), HttpStatus.NOT_FOUND);
        }

        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.set(toReturn);

        return new ResponseEntity<>(customerResponse, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> delete(@PathVariable int id) {
        Customer toDelete = this.customers.findById(id)
                .orElse(
                        null
                );


        if (toDelete == null) {
            ErrorResponse error = new ErrorResponse("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        this.customers.delete(toDelete);
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.set(toDelete);
        return new ResponseEntity<>(customerResponse, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<?>> update(@PathVariable int id, @RequestBody Customer newData) {
        Customer toUpdate = this.customers.findById(id)
                .orElse(
                        null
                );

        // 404 customer not found
        if (toUpdate == null) {
            return new ResponseEntity<>(new ErrorResponse("not found"), HttpStatus.NOT_FOUND);
        }

        // 400 one or more fields are not correct
        if (newData.getEmail() == null || newData.getName() == null || newData.getPhone() == null) {
            return new ResponseEntity<>(new ErrorResponse("bad request"), HttpStatus.BAD_REQUEST);
        }

        toUpdate.setName(newData.getName());
        toUpdate.setEmail(newData.getEmail());
        toUpdate.setPhone(newData.getPhone());
        toUpdate.setUpdatedAt(LocalDateTime.now());

        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.set(this.customers.save(toUpdate));

        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }

}
