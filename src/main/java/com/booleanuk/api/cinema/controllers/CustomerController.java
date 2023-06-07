package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.Dtos.customers.CustomerDtoWithoutTickets;
import com.booleanuk.api.cinema.Dtos.customers.CustomerResponseDto;
import com.booleanuk.api.cinema.entities.Customer;
import com.booleanuk.api.cinema.services.customer.CustomerServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController {
    @Autowired
    CustomerServiceInterface customerService;

    @GetMapping
    public List<CustomerDtoWithoutTickets> getAllCustomer() {
        return customerService.generateList();
    }


    @PostMapping
    public CustomerResponseDto createCustomer(@RequestBody Customer customer) {
        Customer customerFromDb = customerService.createCustomer(customer);
        return new CustomerResponseDto("success", customerService.generateCustomerWithoutTickets(customerFromDb));
    }

    @PutMapping("/{id}")
    public CustomerResponseDto updateCustomer(@PathVariable Integer id, @RequestBody Customer customer) {
        Customer updatedCustomer = customerService.updateCustomer(id, customer);
        return new CustomerResponseDto("success", customerService.generateCustomerWithoutTickets(updatedCustomer));
    }
    @DeleteMapping("/{id}")
    public CustomerResponseDto updateCustomer(@PathVariable Integer id) {
        Customer deletedCustomer = customerService.deleteCustomer(id);
        return new CustomerResponseDto("success", customerService.generateCustomerWithoutTickets(deletedCustomer));
    }
}
