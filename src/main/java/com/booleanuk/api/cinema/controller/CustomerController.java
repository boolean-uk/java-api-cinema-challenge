package com.booleanuk.api.cinema.controller;


import com.booleanuk.api.cinema.helpers.CustomResponse;
import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @GetMapping
    public ResponseEntity<CustomResponse> getAll(){
        CustomResponse response = new CustomResponse("success", customerRepository.findAll());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getById(@PathVariable int id){
        if(!customerRepository.existsById(id)){
            return new ResponseEntity<>(new CustomResponse("error", "No customer with that id were found"), HttpStatus.NOT_FOUND);
        }
        Customer customer = customerRepository.findById(id).get();
        CustomResponse response = new CustomResponse("success", customer);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CustomResponse>  createCustomer(@RequestBody Customer customer){
        if(customer.getEmail() == null || customer.getName() == null || customer.getPhone() == null){
            return new ResponseEntity<>(new CustomResponse("error", "Could not create customer, please check all required fields are correct"), HttpStatus.BAD_REQUEST);
            //throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create customer, please check all required fields are correct");
        }
        customerRepository.save(customer);
        CustomResponse response = new CustomResponse("success", customer);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse> updateCustomer(@PathVariable int id, @RequestBody Customer customer){
        if(!customerRepository.existsById(id)){
            return new ResponseEntity<>(new CustomResponse("error", "No customer with that id were found"), HttpStatus.NOT_FOUND);
        }
        Customer customer1 = customerRepository.findById(id).get();
        customer1.setName(customer.getName());
        customer1.setEmail(customer.getEmail());
        customer1.setPhone(customer.getPhone());
        customer1.setUpdatedAt(LocalDateTime.now(ZoneOffset.UTC));

        if(customer1.getName() == null || customer1.getPhone() == null || customer1.getEmail() == null){
            return new ResponseEntity<>(new CustomResponse("error", "Could not update customer, please check all required fields are correct"), HttpStatus.NOT_FOUND);
        }
        customerRepository.save(customer1);
        CustomResponse response = new CustomResponse("success", customer1);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse> delete(@PathVariable int id){
        if(!customerRepository.existsById(id)){
            return new ResponseEntity<>(new CustomResponse("error", "No customer with that id were found"), HttpStatus.NOT_FOUND);
        }
        Customer customer = customerRepository
                .findById(id).get();
        customerRepository.delete(customer);
        CustomResponse response = new CustomResponse("success", customer);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
