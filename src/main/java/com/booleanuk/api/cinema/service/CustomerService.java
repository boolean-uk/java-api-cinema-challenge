package com.booleanuk.api.cinema.service;

import com.booleanuk.api.cinema.model.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getCustomers();
    Customer getCustomer(Long id);
    Customer createCustomer(Customer customer);
    Customer updateCustomer(Long id, Customer customer);
    Customer deleteCustomer(Long id);
}
