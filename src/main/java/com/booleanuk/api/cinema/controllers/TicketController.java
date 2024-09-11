package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Ticket;
import com.booleanuk.api.cinema.models.Customer;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.repositories.TicketRepository;
import com.booleanuk.api.cinema.repositories.CustomerRepository;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/customers/{customerId}/screenings/{screeningId}")
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ScreeningRepository screeningRepository;

    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTicketsByCustomerAndScreening(@PathVariable int customerId, @PathVariable int screeningId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
        Screening screening = screeningRepository.findById(screeningId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Screening not found"));
        return ResponseEntity.ok(ticketRepository.findByCustomerAndScreening(customer, screening));
    }

    @PostMapping
    public ResponseEntity<Ticket> createTicket(@PathVariable int customerId, @PathVariable int screeningId, @RequestBody Ticket ticket) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
        Screening screening = screeningRepository.findById(screeningId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Screening not found"));
        ticket.setCustomer(customer);
        ticket.setScreening(screening);
        Ticket newTicket = ticketRepository.save(ticket);
        return new ResponseEntity<>(newTicket, HttpStatus.CREATED);
    }
}