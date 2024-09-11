package com.booleanuk.api.cinema.customers;

import com.booleanuk.api.generic.GenericController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("customers")
public class CustomerController extends GenericController<Customer> {
  public CustomerController(CustomerRepository repository) {
    super(repository);
  }
}