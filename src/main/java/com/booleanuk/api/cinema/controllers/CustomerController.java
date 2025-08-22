package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Customer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomerController extends GenericController<Customer, Integer> {
}
