package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.Dtos.CustomerDtoWithoutTickets;
import com.booleanuk.api.cinema.entities.Customer;
import com.booleanuk.api.cinema.repositories.CustomerRepo;
import com.booleanuk.api.cinema.services.CustomerServiceInterface;
import com.booleanuk.api.cinema.services.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController {
    @Autowired
    CustomerServiceInterface customerService;

    @GetMapping
    public List<CustomerDtoWithoutTickets> getAllCustomer(){
        return customerService.generateList();
    }
}
