package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Customer;
import com.booleanuk.api.cinema.repositories.CustomerRepository;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import com.booleanuk.api.cinema.responses.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("customers")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ScreeningRepository screeningRepository;

    /**
     * Gets a list of all customers in the database
     * @return List of Customer objects
     */
    @GetMapping
    public ResponseEntity<CustomerListResponse> getAllCustomers() {
        CustomerListResponse customerListResponse = new CustomerListResponse();
        customerListResponse.set(
                "Successfully returned a list of all the customers",
                this.customerRepository.findAll());
        return ResponseEntity.ok(customerListResponse);
    }

    /**
     * Get one customer
     * @param id - ID of customer
     * @return Response Entity with the results of the request
     */
    @GetMapping("{id}")
    public ResponseEntity<Response<?>> getOneCustomer(@PathVariable int id)  {
        // Check customer id
        Customer customer = this.customerRepository.findById(id)
                .orElse(null);
        if(customer == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No user matching that id was found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.set("Successfully found customer", customer);
        return ResponseEntity.ok(customerResponse);
    }

    /**
     * Add a new customer to the database
     * Request Body:
     *  name: String REQUIRED
     *  email: String REQUIRED
     *  phone: String REQUIRED
     *
     * @param customer - The customer to be added to the database
     * @return Response signifying success/failure, and the customer which was added to the database
     */
    @PostMapping
    public ResponseEntity<Response<?>> createCustomer(@RequestBody Customer customer)  {
        // Check that all required fields are present
        if(!customer.verifyCustomer())
        {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Could not create a new customer, please check all fields are correct");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        CustomerResponse customerResponse = new CustomerResponse();
        this.customerRepository.save(customer);
        customerResponse.set("Successfully created a new customer", customer);
        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }

    /**
     * Update an existing customer to the database
     * Request Body:
     *  name: String REQUIRED
     *  email: String REQUIRED
     *  phone: String REQUIRED
     *
     * @param customer - The customer to be added to the database
     * @return Response signifying success/failure, and the customer which was added to the database
     */
    @PutMapping("{id}")
    public ResponseEntity<Response<?>> updateCustomer(@PathVariable int id, @RequestBody Customer customer)    {
        // Check the customer id
        Customer customerToUpdate = this.customerRepository.findById(id)
                .orElse(null);
        if (customerToUpdate == null)
        {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No customer with that ID found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        // Update all the fields of the customer that are present in request body
        if(customer.getName()  != null) customerToUpdate.setName(customer.getName());
        if(customer.getEmail() != null) customerToUpdate.setEmail(customer.getEmail());
        if(customer.getPhone() != null) customerToUpdate.setPhone(customer.getPhone());

        this.customerRepository.save(customerToUpdate);
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.set("Successfully updated the specified customer", customerToUpdate);
        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }

    /**
     * Deletes a customer by the given id
     * @param id - ID of customer to delete
     * @return Response indicating success/failure, and the deleted customer
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> deleteCustomer(@PathVariable int id)    {
        // Check the customer id
        Customer customerToDelete = this.customerRepository.findById(id)
                .orElse(null);
        if (customerToDelete == null)
        {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No customer with that ID found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        this.customerRepository.delete(customerToDelete);
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.set("Successfully deleted the specified customer", customerToDelete);
        return ResponseEntity.ok(customerResponse);
    }
}
