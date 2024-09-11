package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.dtos.ResponseDTO;
import com.booleanuk.api.cinema.exceptions.BadRequestException;
import com.booleanuk.api.cinema.exceptions.NotFoundException;
import com.booleanuk.api.cinema.models.Customer;
import com.booleanuk.api.cinema.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Customer> customers = customerRepository.findAll();
        ResponseDTO<List<Customer>> response = new ResponseDTO<>(
                "success",
                customers);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Customer customer) {

        try{
            this.customerRepository.save(customer);
            ResponseDTO<Customer> response = new ResponseDTO<>(
                    "success",
                    customer);

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            throw new BadRequestException("bad request");
        }

    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@RequestBody Customer customer, @PathVariable int id) {
        Customer customerToUpdate = findById(id);
        try{
            customerToUpdate.setName(customer.getName());
            customerToUpdate.setEmail(customer.getEmail());
            customerToUpdate.setPhone(customer.getPhone());
            this.customerRepository.save(customerToUpdate);
            ResponseDTO<Customer> response = new ResponseDTO<>(
                    "success",
                    customerToUpdate);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        catch (Exception e){
            throw new BadRequestException("bad request");
        }

    }


    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        Customer customer = findById(id);
        this.customerRepository.delete(customer);

        ResponseDTO<Customer> response = new ResponseDTO<>(
                "success",
                customer);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    private Customer findById(int id) {
        return this.customerRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Customer not found with ID " + id)
        );

    }
}
