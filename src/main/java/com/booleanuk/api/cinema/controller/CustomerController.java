package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.dto.CustomerData;
import com.booleanuk.api.cinema.model.dto.CustomerRequest;
import com.booleanuk.api.cinema.model.dto.CustomerResponse;
import com.booleanuk.api.cinema.model.dto.CustomersResponse;
import com.booleanuk.api.cinema.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CustomersResponse> getAll()
    {
        List<CustomerData> data = customerService.getCustomers().stream().map(c ->{
          return new CustomerData (c.getId(),c.getName(), c.getEmail(), c.getPhone(),c.getCreatedAt(),c.getUpdatedAt());
        }).toList();

        return new ResponseEntity<>(new CustomersResponse("Success",data),HttpStatus.OK);
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Customer> get(@PathVariable(name="id") long id){
        return new ResponseEntity<>( customerService.getCustomer(id),HttpStatus.OK);
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CustomerResponse> update(@PathVariable(name="id") long id,@RequestBody @Valid CustomerRequest customerRequest){

        Customer customer = new Customer();
        customer.setName(customerRequest.name());
        customer.setEmail(customerRequest.email());
        customer.setPhone(customerRequest.phone());

        Customer updatedCustomer = customerService.updateCustomer(id,customer);
        CustomerData data = new CustomerData(
                updatedCustomer.getId(),
                updatedCustomer.getName(),
                updatedCustomer.getEmail(),
                updatedCustomer.getPhone(),
                updatedCustomer.getCreatedAt(),
                updatedCustomer.getUpdatedAt());

        return new ResponseEntity<>(new CustomerResponse("Success",data), HttpStatus.CREATED);
    }
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CustomerResponse> create(@RequestBody @Valid CustomerRequest customerRequest){

        Customer customer = new Customer();
        customer.setName(customerRequest.name());
        customer.setEmail(customerRequest.email());
        customer.setPhone(customerRequest.phone());

        Customer createdCustomer = customerService.createCustomer(customer);
        CustomerData data = new CustomerData(
                createdCustomer.getId(),
                createdCustomer.getName(),
                createdCustomer.getEmail(),
                createdCustomer.getPhone(),
                createdCustomer.getCreatedAt(),
                createdCustomer.getUpdatedAt());

        return new ResponseEntity<>(new CustomerResponse("Success",data),HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomerResponse> delete(@PathVariable(name="id") long id){

        Customer deletedCustomer = customerService.deleteCustomer(id);
        CustomerData data = new CustomerData(
                deletedCustomer.getId(),
                deletedCustomer.getName(),
                deletedCustomer.getEmail(),
                deletedCustomer.getPhone(),
                deletedCustomer.getCreatedAt(),
                deletedCustomer.getUpdatedAt());

        return new ResponseEntity<>(new CustomerResponse("Success",data),HttpStatus.OK);
    }
}
