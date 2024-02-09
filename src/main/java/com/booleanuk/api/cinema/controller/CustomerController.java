package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.repository.CustomerRepository;
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

    @GetMapping
    public List<Customer> getAllCustomers(){
        return this.customerRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Customer> getById(@PathVariable("id") Integer id) {
        Customer customer = this.customerRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cant find "));

        return ResponseEntity.ok(customer);
    }

    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {
        //Regex to make sure the names are strings
        /*String regexString = "/^[a-zA-Z]*$/";
        if(!customer.getName().matches(regexString)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Write the name correctly");
        }
        //Regex for email
        String regexEmail = "^(.+)@(\\S+)$";
        if(!customer.getEmail().matches(regexEmail)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Write a valid email!");
        }

         */

        //Dates
        LocalDateTime currentDateTime = LocalDateTime.now();
        customer.setCreatedAt(currentDateTime);
        customer.setUpdatedAt(null);
        Customer createdCustomer = this.customerRepository.save(customer);
        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateACustomer(@PathVariable int id,@RequestBody Customer customer){
        Customer customerToUpdate = this.customerRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cant find the customer...."));

        //Regex to make sure the name is a string
       /* String regexName = "/^[a-zA-Z]*$/";

        if(!customer.getName().matches(regexName)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Write the name correctly");
        }
        //Regex for email
        String regexEmail = "^(.+)@(\\S+)$";
        if(!customer.getEmail().matches(regexEmail)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Write a valid email!");
        }

        */
        customerToUpdate.setName(customer.getName());
        customerToUpdate.setEmail(customer.getEmail());
        customerToUpdate.setPhone(customer.getPhone());

        //Set update to right now
        LocalDateTime currentDateTime = LocalDateTime.now();
        customerToUpdate.setUpdatedAt(currentDateTime);

        return new ResponseEntity<>(this.customerRepository.save(customerToUpdate),HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> deleteAnCustomer(@PathVariable int id){
        Customer customerToDelete = this.customerRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cant find the customer!!!"));
        this.customerRepository.delete(customerToDelete);
        return ResponseEntity.ok(customerToDelete);
    }
}