
package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.Customer;
import com.booleanuk.api.cinema.DTO.BadRequestException;
import com.booleanuk.api.cinema.DTO.NotFoundException;
//import com.booleanuk.api.cinema.DTO.ErrorResponse;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("customers")
public class CustomerController {
    @Autowired
    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping
    public List<Customer> getAllMovies() {
        return this.customerRepository.findAll();
    }




    @PostMapping
    public ResponseEntity<Customer> createCustomer (@Valid @RequestBody Customer customer){
        return new ResponseEntity<>(this.customerRepository.save(customer), HttpStatus.CREATED);
    }


    @PutMapping("{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable int id, @Valid @RequestBody Customer customer) {
        if(customer.getName() == null || customer.getName().length() < 2) {
            throw new BadRequestException("Name must be at least 2 characters long");
        }

        Customer customerToUpdate = this.customerRepository.findById(id).orElseThrow(
                () -> new NotFoundException("No customer with that ID found")
        );
        // Only update the fields that are being changed
        customerToUpdate.setName(customer.getName());
        customerToUpdate.setEmail(customer.getEmail());
        customerToUpdate.setPhone(customer.getPhone());

        return new ResponseEntity<>(this.customerRepository.save(customerToUpdate), HttpStatus.CREATED);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable int id) {
        Customer customerToDelete = this.customerRepository.findById(id).orElseThrow(
                () -> new NotFoundException("No customer with that ID found")
        );
        this.customerRepository.delete(customerToDelete);
        return ResponseEntity.ok(customerToDelete);
    }


}

