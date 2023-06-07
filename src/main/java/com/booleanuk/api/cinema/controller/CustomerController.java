package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.dto.CustomerListViewDTO;
import com.booleanuk.api.cinema.dto.CustomerViewDTO;
import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("customers")
public class CustomerController {

    private final CustomerRepository customerRepository;
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerRepository customerRepository, CustomerService customerService){
        this.customerRepository = customerRepository;
        this.customerService = customerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerViewDTO create(@RequestBody Customer customer){
        return customerService.create(customer);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CustomerListViewDTO getAll(){
        return customerService.getAll();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerViewDTO update(@PathVariable int id, @RequestBody Customer customer){
        return customerService.update(id, customer);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerViewDTO delete(@PathVariable int id) {
        return customerService.delete(id);
    }
}
