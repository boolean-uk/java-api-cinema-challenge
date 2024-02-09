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

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ticket/customers/{customerId}/screenings/{screeningId}")
public class TicketController {
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ScreeningRepository screeningRepository;

    @PostMapping
    public ResponseEntity<Ticket> create(@PathVariable("screeningId") int screeningId, @PathVariable("customerId") int customerId, @RequestBody Ticket ticket) {
        Screening screening = this.screeningRepository.findById(screeningId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Customer customer= this.customerRepository.findById(customerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        ticket.setScreening(screening);
        ticket.setCustomer(customer);
        return new ResponseEntity<Ticket>(this.ticketRepository.save(ticket), HttpStatus.CREATED);
    }
    @GetMapping
    public List<Ticket> getAll(@PathVariable("screeningId") int screeningId, @PathVariable("customerId") int customerId) {
        Screening screening = this.screeningRepository.findById(screeningId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Customer customer= this.customerRepository.findById(customerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        List<Ticket> tickets = customer.getTickets();
        tickets.retainAll(screening.getTickets());
        return tickets;
    }

}
