package com.booleanuk.api.cinema.controller;
import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("customers/{customer_id}/screenings/{id}")
public class TicketController {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    public ScreeningRepository screeningRepository;
    @Autowired
    public TicketRepository ticketRepository;
    @GetMapping
    public List<Ticket> getAllTickets() {return this.ticketRepository.findAll(); }
    @PostMapping
    public ResponseEntity<Ticket> createScreening(@RequestBody Ticket ticket, @PathVariable int customer_id, @PathVariable int id) {
        Customer tempCustomer = this.customerRepository.findById(customer_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No customer with that ID"));
        Screening tempScreening = this.screeningRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No screening with that ID'"));
        ticket.setCustomer(tempCustomer);
        ticket.setScreening(tempScreening);
        ticket.setCreatedAt(LocalDateTime.now());
        return ResponseEntity.ok(this.ticketRepository.save(ticket));
    }
}