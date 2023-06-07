package com.booleanuk.api.cinema.services.customer;

import com.booleanuk.api.cinema.Dtos.customers.CustomerDtoWithoutTickets;
import com.booleanuk.api.cinema.entities.Customer;

import java.util.List;

public interface CustomerServiceInterface {

    List<CustomerDtoWithoutTickets> generateList();
    Customer createCustomer(Customer customer);
    Customer updateCustomer(int id, Customer customer);

    Customer deleteCustomer(int id);

    CustomerDtoWithoutTickets generateCustomerWithoutTickets(Customer customer);
}
