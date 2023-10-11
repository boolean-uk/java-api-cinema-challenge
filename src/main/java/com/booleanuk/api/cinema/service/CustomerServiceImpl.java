package com.booleanuk.api.cinema.service;

import com.booleanuk.api.cinema.dto.CustomerDTO;
import com.booleanuk.api.cinema.exceptions.EntityNotFoundException;
import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements ICustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    private static Customer convertToCustomer(CustomerDTO dto) {
        return new Customer(dto.getId(), dto.getName(), dto.getEmail(), dto.getPhone(), dto.getCreatedAt(), dto.getUpdatedAt());
    }
    @Override
    public List<Customer> getCustomers() {
        List<Customer> customers;
        customers = customerRepository.findAll();
        return customers;
    }

    @Transactional
    @Override
    public Customer insertCustomer(CustomerDTO customerDTO) {
            return customerRepository.save(convertToCustomer(customerDTO));
    }

    @Transactional
    @Override
    public Customer updateCustomer(CustomerDTO customerDTO) throws EntityNotFoundException {
            Optional<Customer> updatedCustomer = customerRepository.findById(customerDTO.getId());
            if (updatedCustomer.isEmpty())
                throw new EntityNotFoundException(Customer.class, customerDTO.getId());
            return customerRepository.save(convertToCustomer(customerDTO));
    }

    @Transactional
    @Override
    public void deleteCustomer(int id) throws EntityNotFoundException {
        customerRepository.deleteById(id);
    }

    @Override
    public Customer getCustomerById(int id) throws EntityNotFoundException {
        Optional<Customer> customerOpt;
        customerOpt = customerRepository.findById(id);
        if (customerOpt.isEmpty()) throw new EntityNotFoundException(Customer.class, id);
        return customerOpt.get();
    }

}
