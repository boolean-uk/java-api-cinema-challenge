package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Customer;
import com.booleanuk.api.cinema.models.Ticket;
import com.booleanuk.api.cinema.repositories.CustomerRepository;
import com.booleanuk.api.cinema.repositories.TicketRepository;
import com.booleanuk.api.cinema.responses.ApiResponse;
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
    private CustomerRepository customerRepository;
    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Customer>>> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        ApiResponse<List<Customer>> response = new ApiResponse<>("success", customers);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Customer>> getCustomerById(@PathVariable int id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
        ApiResponse<Customer> response = new ApiResponse<>("success", customer);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Customer>> createCustomer(@RequestBody Customer customer) {
        Customer createdCustomer = customerRepository.save(customer);
        ApiResponse<Customer> response = new ApiResponse<>("success", createdCustomer);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Customer>> updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        Customer customerToUpdate = customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        customerToUpdate.setName(customer.getName());
        customerToUpdate.setEmail(customer.getEmail());
        customerToUpdate.setPhone(customer.getPhone());

        Customer updatedCustomer = customerRepository.save(customerToUpdate);
        ApiResponse<Customer> response = new ApiResponse<>("success", updatedCustomer);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Customer>> deleteCustomer(@PathVariable int id) {
        Customer customerToDelete = customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
        customerRepository.delete(customerToDelete);
        ApiResponse<Customer> response = new ApiResponse<>("success", customerToDelete);
        return ResponseEntity.ok(response);
    }

    // Extra routes
    @GetMapping("/{id}/tickets")
    public ResponseEntity<ApiResponse<List<Ticket>>> getTicketsByCustomerId(@PathVariable int id) {
        List<Ticket> tickets = ticketRepository.findByCustomerId(id);
        ApiResponse<List<Ticket>> response = new ApiResponse<>("success", tickets);
        return ResponseEntity.ok(response);
    }
}t