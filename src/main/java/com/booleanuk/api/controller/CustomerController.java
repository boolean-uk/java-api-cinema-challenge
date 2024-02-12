package com.booleanuk.api.controller;

import com.booleanuk.api.model.Customer;
import com.booleanuk.api.repository.CustomerRepository;
import com.booleanuk.api.response.BadRequestResponse;
import com.booleanuk.api.response.NotFoundResponse;
import com.booleanuk.api.response.ResponseTemplate;
import com.booleanuk.api.response.SuccessResponse;
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

    @GetMapping
    public ResponseEntity<ResponseTemplate> getAllCustomers() {
        Object data = this.customerRepository.findAll();
        return new ResponseEntity<>(new SuccessResponse(this.customerRepository.findAll()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseTemplate> createCustomer(@RequestBody Customer customer) {
        if (areAnyFieldsBad(customer)) {
            return new ResponseEntity<>(new BadRequestResponse(), HttpStatus.BAD_REQUEST);
        }
        Customer createdCustomer = this.customerRepository.save(customer);
        createdCustomer.setTickets(new ArrayList<>());
        return new ResponseEntity<>(new SuccessResponse(createdCustomer), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseTemplate> deleteCustomer(@PathVariable int id) {
        if (doesCustomerIDNotExist(id)) {
            return new ResponseEntity<>(new NotFoundResponse(), HttpStatus.NOT_FOUND);
        }
        Customer customerToDelete = this.getCustomerByID(id);
        try {
            this.customerRepository.delete(customerToDelete);
        } catch (Exception e) {
            return new ResponseEntity<>(new BadRequestResponse(), HttpStatus.BAD_REQUEST);
        }
        customerToDelete.setTickets(new ArrayList<>());
        return new ResponseEntity<>(new SuccessResponse(customerToDelete), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseTemplate> updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        if (areAnyFieldsBad(customer)) {
            return new ResponseEntity<>(new BadRequestResponse(), HttpStatus.BAD_REQUEST);
        }
        if (doesCustomerIDNotExist(id)) {
            return new ResponseEntity<>(new NotFoundResponse(), HttpStatus.NOT_FOUND);
        }
        Customer customerToUpdate = getCustomerByID(id);
        customerToUpdate.setName(customer.getName());
        customerToUpdate.setEmail(customer.getEmail());
        customerToUpdate.setPhone(customer.getPhone());
        this.customerRepository.save(customerToUpdate);
        return new ResponseEntity<>(new SuccessResponse(customerToUpdate), HttpStatus.CREATED);
    }

    //--------------------------- Private section---------------------------//

    private Customer getCustomerByID(int id) {
        for (Customer customer : this.customerRepository.findAll()) {
            if (customer.getId() == id) {
                return customer;
            }
        }
        return new Customer();
    }

    private boolean doesCustomerIDNotExist(int id) {
        for (Customer customer : this.customerRepository.findAll()) {
            if (customer.getId() == id) {
                return false;
            }
        }
        return true;
    }

    private boolean areAnyFieldsBad(Customer customer) {
        if (customer.getName() == null ||
            customer.getEmail() == null ||
            customer.getPhone() == null ||
            customer.getName().isBlank() ||
            customer.getEmail().isBlank() ||
            customer.getPhone().isBlank())
        {
            return true;
        }
        return false;
    }
}
