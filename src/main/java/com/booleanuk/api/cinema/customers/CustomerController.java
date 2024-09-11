package com.booleanuk.api.cinema.customers;

import com.booleanuk.api.base.Controller;

public class CustomerController extends Controller<Customer> {
  public CustomerController(CustomerRepository repository) {
    super(repository);
  }
}
