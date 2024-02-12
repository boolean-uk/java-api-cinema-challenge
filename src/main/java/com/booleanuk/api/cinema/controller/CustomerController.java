package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.exceptions.CustomDataNotFoundException;
import com.booleanuk.api.cinema.exceptions.CustomParameterConstraintException;
import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.util.DateCreater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        customer.setCreatedAt(DateCreater.getCurrentDate());
        customer.setUpdatedAt(DateCreater.getCurrentDate());
        this.areCustomerValid(customer);
        return ResponseEntity.ok(this.customerRepository.save(customer));
    }


    @GetMapping
    public List<Customer> getCustomers() {
        return this.customerRepository.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable (name = "id") int id, @RequestBody Customer customer) {
        Customer customerToUpdate = this.getACustomer(id);
        customerToUpdate.setName(customer.getName());
        customerToUpdate.setEmail(customer.getEmail());
        customerToUpdate.setUpdatedAt(DateCreater.getCurrentDate());
        customerToUpdate.setPhone(customer.getPhone());

        this.areCustomerValid(customerToUpdate);
        return new ResponseEntity<>(this.customerRepository.save(customerToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable (name = "id") int id) {
        Customer customerToDelete = this.getACustomer(id);
        this.customerRepository.delete(customerToDelete);
        return new ResponseEntity<>(customerToDelete, HttpStatus.OK);
    }

    private Customer getACustomer(int id) {
        return this.customerRepository.findById(id).orElseThrow(() -> new CustomDataNotFoundException("No customer with that ID found"));
    }

    private void areCustomerValid(Customer customer) {
        if(customer.getName() == null || customer.getPhone() == null || customer.getEmail() == null || customer.getUpdatedAt() == null || customer.getCreatedAt() == null) {
            throw new CustomParameterConstraintException("Could not create a new customer, please check all fields are correct");
        }

    }
}
