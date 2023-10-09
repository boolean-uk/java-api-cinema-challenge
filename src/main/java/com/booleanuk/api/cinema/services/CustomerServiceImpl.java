package com.booleanuk.api.cinema.services;

import com.booleanuk.api.cinema.domain.dtos.CreateCustomerRequestDTO;
import com.booleanuk.api.cinema.domain.dtos.CustomerResponseDTO;
import com.booleanuk.api.cinema.domain.dtos.UpdateCustomerRequestDTO;
import com.booleanuk.api.cinema.domain.entities.Customer;
import com.booleanuk.api.cinema.errors.ResourceNotFoundException;
import com.booleanuk.api.cinema.repositories.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
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
    public CustomerResponseDTO createCustomer(CreateCustomerRequestDTO customerDTO) {
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        customer.setCreatedAt(LocalDateTime.now());
        Customer savedCustomer = customerRepository.save(customer);
        return modelMapper.map(savedCustomer, CustomerResponseDTO.class);
    }
    @Override
    public CustomerResponseDTO updateCustomer(Long id, UpdateCustomerRequestDTO updateCustomerDTO) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found."));
        if (hasUpdates(existingCustomer, updateCustomerDTO)) {
            existingCustomer.setUpdatedAt(LocalDateTime.now());
        }
        existingCustomer.setName(updateCustomerDTO.getName());
        existingCustomer.setEmail(updateCustomerDTO.getEmail());
        existingCustomer.setPhone(updateCustomerDTO.getPhone());

        Customer updatedCustomer = customerRepository.save(existingCustomer);

        CustomerResponseDTO customerDTO = modelMapper.map(updatedCustomer, CustomerResponseDTO.class);

        return customerDTO;
    }

    @Override
    public void deleteCustomer(Long customerId) {
        if (customerRepository.existsById(customerId)) {
            customerRepository.deleteById(customerId);
        } else {
            throw new ResourceNotFoundException("Customer not found with id: " + customerId);
        }
    }


    // Helper method to check if there are updates in the DTO
    private boolean hasUpdates(Customer existingCustomer, UpdateCustomerRequestDTO updateCustomerDTO) {
        boolean hasUpdates = !existingCustomer.getName().equals(updateCustomerDTO.getName()) ||
                !existingCustomer.getEmail().equals(updateCustomerDTO.getEmail()) ||
                !existingCustomer.getPhone().equals(updateCustomerDTO.getPhone());

        return hasUpdates;
    }
}