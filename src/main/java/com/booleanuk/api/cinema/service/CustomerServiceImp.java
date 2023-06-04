package com.booleanuk.api.cinema.service;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class CustomerServiceImp implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomer(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Not found"));
    }

    @Override
    public Customer createCustomer(Customer customer) {
        LocalDateTime createdAt = LocalDateTime.now();
        customer.setCreatedAt(createdAt);
        customer.setUpdatedAt(createdAt);
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Long id, Customer customer) {
        Customer foundCustomer = customerRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"No customer matches the provided id"));

        customer.setId(id);
        customer.setCreatedAt(foundCustomer.getCreatedAt());
        customer.setUpdatedAt(LocalDateTime.now());
        return customerRepository.save(customer);
    }

    @Override
    public Customer deleteCustomer(Long id) {
        Customer toBeDeleted = customerRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"No customer matches the provided id"));

        customerRepository.delete(toBeDeleted);
        return toBeDeleted;
    }
}
