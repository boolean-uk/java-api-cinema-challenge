package com.booleanuk.api.controller;

import com.booleanuk.api.model.Customer;
import com.booleanuk.api.model.Ticket;
import com.booleanuk.api.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;


    @GetMapping("/{id}")
    public ResponseEntity<Customer> getById(@PathVariable int id){
        Customer employee = this.customerRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        return ResponseEntity.ok(employee);
    }
    @GetMapping
    public List<Customer> getAll(){
        return this.customerRepository.findAll();
    }
    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody Customer employee){
        return new ResponseEntity<Customer>(this.customerRepository.save(employee), HttpStatus.CREATED) ;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> delete(@PathVariable int id) {
        Customer toDelete = this.customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No publisher with ID"));
        this.customerRepository.delete(toDelete);
        toDelete.setTickets(new ArrayList<Ticket>());
        return ResponseEntity.ok(toDelete);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(@PathVariable int id, @RequestBody Customer employee){
        Customer update = this.customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        update.setName(employee.getName());
        update.setEmail(employee.getEmail());
        update.setEmail(employee.getEmail());
        update.setPhone(employee.getPhone());
        update.setCreatedAt(employee.getCreatedAt());
        update.setUpdatedAt(employee.getUpdatedAt());
        update.setTickets(new ArrayList<>());
        return new ResponseEntity<>(this.customerRepository.save(update), HttpStatus.CREATED);
    }
}
