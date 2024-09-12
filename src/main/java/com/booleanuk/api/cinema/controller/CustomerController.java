package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Customer create(@RequestBody Customer body) {
        return this.customerRepository.save(body);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Customer> getAll() {
        return this.customerRepository.findAll();
    }

    @PutMapping("{id}")
    public Customer update(@PathVariable int id, @RequestBody Customer body) {
        return this.customerRepository.findById(id)
                .map(customer -> {
                    customer.setName(body.getName());
                    customer.setEmail(body.getEmail());
                    customer.setPhone(body.getPhone());
                    return this.customerRepository.save(customer);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
    }

    @DeleteMapping("{id}")
    public Customer delete(@PathVariable int id) {
        return this.customerRepository.findById(id)
                .map(customer -> {
                    this.customerRepository.delete(customer);
                    return customer;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
    }
}
