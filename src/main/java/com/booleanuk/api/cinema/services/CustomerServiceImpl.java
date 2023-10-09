package com.booleanuk.api.cinema.services;

import com.booleanuk.api.cinema.domain.dtos.CreateCustomerRequestDTO;
import com.booleanuk.api.cinema.domain.dtos.CustomerResponseDTO;
import com.booleanuk.api.cinema.domain.dtos.UpdateCustomerRequestDTO;
import com.booleanuk.api.cinema.domain.entities.Customer;
import com.booleanuk.api.cinema.errors.ResourceNotFoundException;
import com.booleanuk.api.cinema.repositories.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CustomerResponseDTO createCustomer(CreateCustomerRequestDTO customerDTO) {
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        Customer savedCustomer = customerRepository.save(customer);
        return modelMapper.map(savedCustomer, CustomerResponseDTO.class);
    }

    @Override
    public List<CustomerResponseDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(customer -> modelMapper.map(customer, CustomerResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public CustomerResponseDTO getCustomerById(Long customerId) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isPresent()) {
            return modelMapper.map(customerOptional.get(), CustomerResponseDTO.class);
        } else {
            throw new ResourceNotFoundException("Customer not found with id: " + customerId);
        }
    }

    @Override
    public CustomerResponseDTO updateCustomer(Long customerId, UpdateCustomerRequestDTO customerDTO) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isPresent()) {
            Customer existingCustomer = customerOptional.get();
            modelMapper.map(customerDTO, existingCustomer);
            Customer updatedCustomer = customerRepository.save(existingCustomer);
            return modelMapper.map(updatedCustomer, CustomerResponseDTO.class);
        } else {
            throw new ResourceNotFoundException("Customer not found with id: " + customerId);
        }
    }

    @Override
    public void deleteCustomer(Long customerId) {
        if (customerRepository.existsById(customerId)) {
            customerRepository.deleteById(customerId);
        } else {
            throw new ResourceNotFoundException("Customer not found with id: " + customerId);
        }
    }
}