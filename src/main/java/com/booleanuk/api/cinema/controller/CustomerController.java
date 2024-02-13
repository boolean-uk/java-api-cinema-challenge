package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.TicketRepository;
import com.booleanuk.api.cinema.response.*;
import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.repository.CustomerRepository;
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
    @Autowired
    private TicketRepository ticketRepository;

    private Customer getACustomer(int id) {
        return this.customerRepository.findById(id).orElse(null);
    }
    @GetMapping
    public ResponseEntity<CustomerListResponse> getAll() {
        CustomerListResponse customerListResponse = new CustomerListResponse();
        customerListResponse.set(this.customerRepository.findAll());
        return ResponseEntity.ok(customerListResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseGeneric<?>> getOne(@PathVariable int id){
        Customer customer = this.customerRepository.findById(id).orElse(null);
        if(customer == null){
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.set(customer);
        return ResponseEntity.ok(customerResponse);
        //return ResponseEntity.ok(new Response<>("success",this.getACustomer(id)));
    }
    @PostMapping
    public ResponseEntity<ResponseGeneric<?>> createCustomer(@RequestBody Customer customer){
        if(customer.getName() == null || customer.getEmail() == null || customer.getPhone() == null){
            ErrorResponse error = new ErrorResponse();
            error.set("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        Customer savedCustomer = this.customerRepository.save(customer);
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.set(savedCustomer);

        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ResponseGeneric<?>> updateCustomer(@PathVariable int id, @RequestBody Customer customer){
        Customer customerToUpdate = this.getACustomer(id);
        if(customer.getName() == null || customer.getEmail() == null || customer.getPhone() == null){
            ErrorResponse error = new ErrorResponse();
            error.set("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        customerToUpdate.setName(customer.getName());
        customerToUpdate.setEmail(customer.getEmail());
        customerToUpdate.setPhone(customer.getPhone());
        //customerToUpdate.setCreatedAt(customer.getCreatedAt());
        customerToUpdate.setUpdatedAt(customer.getUpdatedAt());

        Customer updatedCustomer = this.customerRepository.save(customerToUpdate);
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.set(updatedCustomer);

        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseGeneric<?>> deleteCustomer (@PathVariable int id){
        Customer customerToDelete = this.getACustomer(id);

        List<Ticket> tickets = customerToDelete.getTickets();
        for (Ticket ticket : tickets){
            ticketRepository.delete(ticket);
        }
        customerToDelete.setTickets(new ArrayList<>());

        this.customerRepository.delete(customerToDelete);
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.set(customerToDelete);
        return  ResponseEntity.ok(customerResponse);
    }






}
