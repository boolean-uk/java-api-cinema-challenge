package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Ticket;
import com.booleanuk.api.cinema.models.Customer;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.repositories.TicketRepository;
import com.booleanuk.api.cinema.repositories.CustomerRepository;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import com.booleanuk.api.cinema.responses.Response;
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

    private final Response<Object> response = new Response<>();

    @GetMapping
    public ResponseEntity<?> getAllTicketsByCustomerAndScreening(@PathVariable int customerId, @PathVariable int screeningId) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        Screening screening = screeningRepository.findById(screeningId).orElse(null);
        if (customer == null || screening == null) {
            response.setError("not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        List<Ticket> tickets = ticketRepository.findByCustomerAndScreening(customer, screening);
        response.setSuccess(tickets);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> createTicket(@PathVariable int customerId, @PathVariable int screeningId, @RequestBody Ticket ticket) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        Screening screening = screeningRepository.findById(screeningId).orElse(null);
        if (customer == null || screening == null) {
            response.setError("not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        try {
            ticket.setCustomer(customer);
            ticket.setScreening(screening);
            Ticket newTicket = ticketRepository.save(ticket);
            response.setSuccess(newTicket);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.setError("bad request");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}