package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController {
    @Autowired
    private CustomerRepository repository;
    @GetMapping
    public List<Customer> getAll(){
        return this.repository.findAll();
    }
    //not necessary but will come in handy later
    @GetMapping("/{id}")
    public Customer getCustomer(@PathVariable int id){
        return this.repository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Customer with given id not found"));
    }
    public record CustomerRequest(String name, String email,String phone){}
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody CustomerRequest customer){
        if(customer.name == null || customer.email == null || customer.phone == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid body for Customer");
        }
        Customer createdCustomer = new Customer(customer.name,customer.email, customer.phone);
        return new ResponseEntity<>(createdCustomer,HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable int id, @RequestBody CustomerRequest customer){
        Customer updatedCustomer = this.getCustomer(id);
        if(customer.name != null && !customer.name.isEmpty()){
            updatedCustomer.setName(customer.name);
            updatedCustomer.setUpdatedAt();
        }
        if(customer.phone != null && !customer.phone.isEmpty()){
            updatedCustomer.setPhone(customer.phone);
            updatedCustomer.setUpdatedAt();
        }
        if(customer.email != null && !customer.email.isEmpty()){
            updatedCustomer.setEmail(customer.email);
            updatedCustomer.setUpdatedAt();
        }
        return new ResponseEntity<>(updatedCustomer,HttpStatus.OK);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable int id){
        Customer toDelete = getCustomer(id);
        this.repository.delete(toDelete);
        return new ResponseEntity<>(toDelete,HttpStatus.OK);
    }
}
