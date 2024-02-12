package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Customer;
import com.booleanuk.api.cinema.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("customers")
public class CustomerController {
    private LocalDateTime today;
    private final DateTimeFormatter pattern = DateTimeFormatter.ISO_LOCAL_DATE_TIME.localizedBy(Locale.UK);
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public List<Customer> getAll(){
        return this.customerRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable int id){
        Customer findCustomer = findOne(id);
        return ResponseEntity.ok(findCustomer);
    }

    @PostMapping
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer){
        today = LocalDateTime.now();



        customer.setCreatedAt(today.format(pattern));
        customer.setUpdatedAt(today.format(pattern));
        return new ResponseEntity<Customer>(this.customerRepository.save(customer),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable int id, @RequestBody Customer customer){
        today = LocalDateTime.now();

        Customer updateCustomer = findOne(id);
        Optional.ofNullable(customer.getName())
                .ifPresent(updateCustomer::setName);

        Optional.ofNullable(customer.getEmail())
                .ifPresent(updateCustomer::setEmail);

        Optional.ofNullable(customer.getPhone())
                        .ifPresent(updateCustomer::setPhone);

        updateCustomer.setUpdatedAt(today.format(pattern));
        return new ResponseEntity<Customer>(this.customerRepository.save(updateCustomer),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable int id){
        Customer deleteCustomer = findOne(id);
        this.customerRepository.delete(deleteCustomer);
        deleteCustomer.setTickets(new ArrayList<>());
        return ResponseEntity.ok(deleteCustomer);
    }

    private Customer findOne(int id){
        return this.customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}
