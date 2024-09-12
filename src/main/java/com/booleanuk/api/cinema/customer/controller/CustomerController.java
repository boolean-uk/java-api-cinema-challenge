package com.booleanuk.api.cinema.customer.controller;

import com.booleanuk.api.cinema.customer.model.Customer;
import com.booleanuk.api.cinema.customer.repository.CustomerRepository;
import com.booleanuk.api.cinema.response.ErrorResponse;
import com.booleanuk.api.cinema.response.ResponseInterface;
import com.booleanuk.api.cinema.response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @PostMapping
    public ResponseEntity<ResponseInterface> addCustomer(@RequestBody Customer customer) {
        ResponseInterface response;

        try {
            Customer newCustomer = this.customerRepository.save(customer);
            response = new SuccessResponse<>(newCustomer);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("bad request"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<ResponseInterface> getAllCustomers() {
        ResponseInterface response = new SuccessResponse<>(this.customerRepository.findAll());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseInterface> getCustomerById(@PathVariable (name = "id") int id) {
        ResponseInterface response;
        Customer customer = findCustomerById(id);
        if (customer == null) {
            response = new ErrorResponse("not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        response = new SuccessResponse<>(customer);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseInterface> updateCustomer(@PathVariable (name = "id") int id, @RequestBody Customer customer) {
        ResponseInterface response;
        Customer customerToUpdate = findCustomerById(id);

        if (customerToUpdate == null) {
            response = new ErrorResponse("not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            customerToUpdate.setName(customer.getName());
            customerToUpdate.setPhone(customer.getPhone());
            customerToUpdate.setEmail(customer.getEmail());
            customerToUpdate.setUpdatedAt(OffsetDateTime.now());

            response = new SuccessResponse<>(this.customerRepository.save(customerToUpdate));
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            response = new ErrorResponse("bad request");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseInterface> deleteCustomer(@PathVariable (name = "id") int id){
        ResponseInterface response;
        Customer customerToDelete = findCustomerById(id);

        if (customerToDelete == null) {
            response = new ErrorResponse("not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            this.customerRepository.delete(customerToDelete);
            response = new SuccessResponse<>(customerToDelete);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response = new ErrorResponse("bad request");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /* Helper functions */
    private Customer findCustomerById(int id) {
        return this.customerRepository.findById(id).orElse(null);
    }
}
