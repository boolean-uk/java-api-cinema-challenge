package com.booleanuk.api.cinema.service;

import com.booleanuk.api.cinema.dto.CustomerDTO;
import com.booleanuk.api.cinema.exceptions.EntityNotFoundException;
import com.booleanuk.api.cinema.model.Customer;

import java.util.List;

public interface ICustomerService {

    List<Customer> getCustomers ();
    Customer insertCustomer(CustomerDTO customerDTO);
    Customer updateCustomer(CustomerDTO customerDTO) throws EntityNotFoundException;
    void deleteCustomer(int id) throws EntityNotFoundException;
    Customer getCustomerById(int id) throws EntityNotFoundException;

}
