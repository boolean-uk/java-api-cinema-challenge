package com.booleanuk.api.cinema.library.controllers;

import com.booleanuk.api.cinema.library.models.Customer;
import com.booleanuk.api.cinema.library.models.Screening;
import com.booleanuk.api.cinema.library.models.Ticket;
import com.booleanuk.api.cinema.library.payload.request.CustomerRequest;
import com.booleanuk.api.cinema.library.payload.response.CustomerResponse;
import com.booleanuk.api.cinema.library.repository.CustomerRepository;
import com.booleanuk.api.cinema.library.repository.ScreeningRepository;
import com.booleanuk.api.cinema.library.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping
    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable int id) {
        return customerRepository.findById(id)
                .map(customer -> ResponseEntity.ok(toResponse(customer)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CustomerRequest request) {
        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());

        Customer saved = customerRepository.save(customer);
        return ResponseEntity.status(201).body(toResponse(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable int id, @RequestBody CustomerRequest request) {
        return customerRepository.findById(id).map(customer -> {
            customer.setName(request.getName());
            customer.setEmail(request.getEmail());
            customer.setPhone(request.getPhone());

            Customer updated = customerRepository.save(customer);
            return ResponseEntity.ok(toResponse(updated));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable int id) {
        if (!customerRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        customerRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private CustomerResponse toResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .build();
    }

}
