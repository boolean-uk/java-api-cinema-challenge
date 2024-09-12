package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.ApiResponse;
import com.booleanuk.api.cinema.exception.NotFoundException;
import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController {
    private static final String SUCCESS = "success";
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Customer> create(@Valid @RequestBody Customer body) {
        Customer customer = this.customerRepository.save(body);
        return new ApiResponse<>(SUCCESS, customer);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<List<Customer>> getAll() {
        return new ApiResponse<>(SUCCESS, this.customerRepository.findAll());
    }

    @PutMapping("{id}")
    public ApiResponse<Customer> update(@PathVariable int id, @Valid @RequestBody Customer body) {
        return this.customerRepository.findById(id)
                .map(customer -> {
                    customer.setName(body.getName());
                    customer.setEmail(body.getEmail());
                    customer.setPhone(body.getPhone());
                    return new ApiResponse<>(SUCCESS, this.customerRepository.save(customer));
                })
                .orElseThrow(() -> new NotFoundException("not found"));
    }

    @DeleteMapping("{id}")
    public ApiResponse<Customer> delete(@PathVariable int id) {
        return this.customerRepository.findById(id)
                .map(customer -> {
                    this.customerRepository.delete(customer);
                    return new ApiResponse<>(SUCCESS, customer);
                })
                .orElseThrow(() -> new NotFoundException("not found"));
    }
}
