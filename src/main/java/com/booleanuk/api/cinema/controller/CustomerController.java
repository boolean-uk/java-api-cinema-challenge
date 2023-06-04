package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Customer> getAll(){
        return customerService.getCustomers();
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Customer get(@PathVariable(name="id") long id){
        return customerService.getCustomer(id);
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Customer update(@PathVariable(name="id") long id,@RequestBody Customer customer){
        return customerService.updateCustomer(id,customer);
    }
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Customer create(@RequestBody Customer customer){
        return customerService.createCustomer(customer);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Customer delete(@PathVariable(name="id") long id){
        return customerService.deleteCustomer(id);
    }
}
