package com.booleanuk.api.cinema.services;

import com.booleanuk.api.cinema.domain.dtos.CreateCustomerRequestDTO;
import com.booleanuk.api.cinema.domain.dtos.CustomerResponseDTO;
import com.booleanuk.api.cinema.domain.dtos.UpdateCustomerRequestDTO;

import java.util.List;

public interface CustomerService {
    CustomerResponseDTO createCustomer(CreateCustomerRequestDTO customerDTO);

    List<CustomerResponseDTO> getAllCustomers();

    CustomerResponseDTO getCustomerById(Long customerId);

    CustomerResponseDTO updateCustomer(Long customerId, UpdateCustomerRequestDTO customerDTO);

    void deleteCustomer(Long customerId);
}