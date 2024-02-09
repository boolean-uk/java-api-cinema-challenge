package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ScreeningRepository screeningRepository;
    @Autowired
    private TicketRepository ticketRepository;
    private LocalDateTime time = LocalDateTime.now();

    @GetMapping
    public List<Customer> getAllCustomers() {
        return this.customerRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        customer.setCreatedAt(time);
        customer.setUpdatedAt(time);
        Customer newCustomer = this.customerRepository.save(customer);
        newCustomer.setTickets(new ArrayList<>());
        return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        Customer customerToUpdate = customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer was not found"));
        customerToUpdate.setUpdatedAt(time);
        customerToUpdate.setName(customer.getName());
        customerToUpdate.setEmail(customer.getEmail());
        customerToUpdate.setPhone(customer.getPhone());
        return new ResponseEntity<>(customerToUpdate, HttpStatus.CREATED);
    }

    @GetMapping("/{customerId}/screenings/{screeningId}")
    public ResponseEntity<List<Ticket>> getAllTicketsFromScreening(@PathVariable int customerId, @PathVariable int screeningId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        Screening screening = screeningRepository.findById(screeningId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        List<Ticket> customerTickets = customer.getTickets();
        List<Ticket> screeningTickets = screening.getTickets();
        List<Ticket> combinedTickets = new ArrayList<>(customerTickets);
        combinedTickets.retainAll(screeningTickets);
        return new ResponseEntity<>(combinedTickets, HttpStatus.OK);
    }

    @PostMapping("/{customerId}/screenings/{screeningId}")
    public ResponseEntity<Ticket> createTicket(@PathVariable int customerId, @PathVariable int screeningId, @RequestBody Ticket ticket) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        Screening screening = screeningRepository.findById(screeningId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        ticket.setCreatedAt(time);
        ticket.setUpdatedAt(time);
        ticket.setCustomer(customer);
        ticket.setScreening(screening);
        return new ResponseEntity<>(this.ticketRepository.save(ticket), HttpStatus.CREATED);
    }

}
