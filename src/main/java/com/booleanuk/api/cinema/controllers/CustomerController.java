package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.Dtos.customers.CustomerResponseDto;
import com.booleanuk.api.cinema.Dtos.tickets.TicketResponseDto;
import com.booleanuk.api.cinema.Dtos.tickets.TicketResponseSingleDto;
import com.booleanuk.api.cinema.entities.Customer;
import com.booleanuk.api.cinema.entities.Ticket;
import com.booleanuk.api.cinema.services.customer.CustomerServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController {
    @Autowired
    CustomerServiceInterface customerService;


    @GetMapping
    public List<Customer> getAllCustomer() {
        return customerService.getAllCustomers();
    }
    @PostMapping
    public CustomerResponseDto createCustomer(@RequestBody Customer customer) {
        Customer customerFromDb = customerService.createCustomer(customer);
        return new CustomerResponseDto("success", customerFromDb);
    }

    @PutMapping("/{id}")
    public CustomerResponseDto updateCustomer(@PathVariable Integer id, @RequestBody Customer customer) {
        Customer updatedCustomer = customerService.updateCustomer(id, customer);
        return new CustomerResponseDto("success", updatedCustomer);
    }
    @DeleteMapping("/{id}")
    public CustomerResponseDto updateCustomer(@PathVariable Integer id) {
        Customer deletedCustomer = customerService.deleteCustomer(id);
        return new CustomerResponseDto("success", deletedCustomer);
    }


    @GetMapping("/{customerId}/screenings/{screeningId}")
    public TicketResponseDto getAllTicketsOfaCustomerOfAScreening(@PathVariable Integer customerId, @PathVariable Integer screeningId) {
        return new TicketResponseDto("success", customerService.findByCustomerIdAndScreeningId(customerId, screeningId));
    }

    @PostMapping("/{customerId}/screenings/{screeningId}")
    public TicketResponseSingleDto CreateTicketOfaCustomerOfAScreening(@PathVariable Integer customerId, @PathVariable Integer screeningId, @RequestBody Ticket ticket) {
        return new TicketResponseSingleDto("success", customerService.createTicket(customerId, screeningId, ticket));
    }
}
