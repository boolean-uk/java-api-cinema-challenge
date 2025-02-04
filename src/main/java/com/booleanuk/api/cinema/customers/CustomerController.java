package com.booleanuk.api.cinema.customers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{id}")
    public Customer getOneCustomer(@PathVariable int id) {
        return this.customerRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found.")
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Customer> getAllCustomers() {
        return this.customerRepository.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        Customer newCustomer = new Customer(customer.getName(), customer.getEmail(), customer.getPhone());
        return this.customerRepository.save(newCustomer);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("{id}")
    public Customer updateCustomer(@RequestBody Customer customer, @PathVariable int id) {
        Customer customerToUpdate = this.customerRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found.")
        );
        customerToUpdate.setName(customer.getName());
        customerToUpdate.setEmail(customer.getEmail());
        customerToUpdate.setPhone(customer.getPhone());
        return this.customerRepository.save(customerToUpdate);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("{id}")
    public Customer deleteCustomer(@PathVariable int id) {
        Customer customerToDelete = this.customerRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found.")
        );
        this.customerRepository.delete(customerToDelete);
        return customerToDelete;
    }
}
