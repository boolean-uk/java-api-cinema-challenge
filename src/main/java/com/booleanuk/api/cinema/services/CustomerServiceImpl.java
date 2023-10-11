package com.booleanuk.api.cinema.services;

import com.booleanuk.api.cinema.domain.dtos.CreateCustomerRequestDTO;
import com.booleanuk.api.cinema.domain.dtos.CustomerResponseDTO;
import com.booleanuk.api.cinema.domain.dtos.UpdateCustomerRequestDTO;
import com.booleanuk.api.cinema.domain.entities.Customer;
import com.booleanuk.api.cinema.errors.ResourceNotFoundException;
import com.booleanuk.api.cinema.repositories.CustomerRepository;
import com.booleanuk.api.cinema.utils.ErrorConstants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
            throw new ResourceNotFoundException(String.format(ErrorConstants.CUSTOMER_NOT_FOUND_MESSAGE, customerId));
        }
    }

    @Override
    public CustomerResponseDTO createCustomer(CreateCustomerRequestDTO customerDTO) {
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        LocalDateTime currentDateTime = LocalDateTime.now();
        customer.setCreatedAt(currentDateTime);
        customer.setUpdatedAt(currentDateTime);
        Customer savedCustomer = customerRepository.save(customer);
        return modelMapper.map(savedCustomer, CustomerResponseDTO.class);
    }

    @Override
    public CustomerResponseDTO updateCustomer(Long id, UpdateCustomerRequestDTO updateCustomerDTO) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorConstants.CUSTOMER_NOT_FOUND_MESSAGE, id)));
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
    public CustomerResponseDTO deleteCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorConstants.CUSTOMER_NOT_FOUND_MESSAGE,customerId)));
        customerRepository.delete(customer);
        return modelMapper.map(customer, CustomerResponseDTO.class);
    }

    private boolean hasUpdates(Customer existingCustomer, UpdateCustomerRequestDTO updateCustomerDTO) {
        boolean hasUpdates = !existingCustomer.getName().equals(updateCustomerDTO.getName()) ||
                !existingCustomer.getEmail().equals(updateCustomerDTO.getEmail()) ||
                !existingCustomer.getPhone().equals(updateCustomerDTO.getPhone());

        return hasUpdates;
    }
}