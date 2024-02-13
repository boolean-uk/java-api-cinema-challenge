package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.exceptions.CustomDataNotFoundException;
import com.booleanuk.api.cinema.exceptions.CustomParameterConstraintException;
import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.util.DateCreater;
import com.booleanuk.api.cinema.util.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping
    public ResponseEntity<SuccessResponse<?>> createCustomer(@RequestBody Customer customer) {
        customer.setCreatedAt(DateCreater.getCurrentDate());
        customer.setUpdatedAt(DateCreater.getCurrentDate());
        this.areCustomerValid(customer);
        if(customer.getTickets() == null) {
            customer.setTickets(new ArrayList<>());

        }
        this.customerRepository.save(customer);
        SuccessResponse<Customer> successResponse = new SuccessResponse<>(customer);
        return new ResponseEntity<>(successResponse, HttpStatus.CREATED);    }


    @GetMapping
    public ResponseEntity<SuccessResponse<List<Customer>>> getCustomers() {

        List<Customer> data = this.customerRepository.findAll();
        SuccessResponse<List<Customer>> successResponse = new SuccessResponse<>(data);
        return ResponseEntity.ok(successResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse<?>> updateCustomer(@PathVariable (name = "id") int id, @RequestBody Customer customer) {
        Customer customerToUpdate = this.getACustomer(id);
        customerToUpdate.setName(customer.getName());
        customerToUpdate.setEmail(customer.getEmail());
        customerToUpdate.setUpdatedAt(DateCreater.getCurrentDate());
        customerToUpdate.setPhone(customer.getPhone());

        this.areCustomerValid(customerToUpdate);

        SuccessResponse<Customer> successResponse = new SuccessResponse<>(customerToUpdate);
        this.customerRepository.save(customerToUpdate);
        return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<SuccessResponse<?>> deleteCustomer(@PathVariable (name = "id") int id) {
        Customer customerToDelete = this.getACustomer(id);

        this.customerRepository.delete(customerToDelete);
        SuccessResponse<Customer> successResponse = new SuccessResponse<>(customerToDelete);
        return ResponseEntity.ok(successResponse);
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
