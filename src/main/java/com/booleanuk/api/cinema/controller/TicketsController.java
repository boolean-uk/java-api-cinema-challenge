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

import java.util.List;

@RestController
public class TicketsController {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ScreeningRepository screeningRepository;

    @Autowired
    TicketRepository ticketRepository;

    @GetMapping("/customers/{customerId}/screenings/{screeningId}")
    public List<Ticket> getAll(@PathVariable int customerId, @PathVariable int screeningId){
        Customer customer = null;
        Screening screening = null;
        customer = this.customerRepository.findById(customerId).orElseThrow( () -> new ResponseStatusException((HttpStatus.NOT_FOUND), "Customer's Id was not found"));
        screening = this.screeningRepository.findById(screeningId).orElseThrow( () -> new ResponseStatusException((HttpStatus.NOT_FOUND), "Screening's ID not found"));
        return this.ticketRepository.findByCustomerAndScreeningId(customerId, screeningId);

    }

    @PostMapping("/customers/{customerId}/screenings/{screeningId}")
    public ResponseEntity<Ticket> createTicket(@PathVariable int customerId, @PathVariable int screeningId, @RequestBody Ticket ticket){
        Customer customer = null;
        Screening screening = null;
        customer = this.customerRepository.findById(customerId).orElseThrow( () -> new ResponseStatusException((HttpStatus.NOT_FOUND), "Customer's Id was not found"));
        screening = this.screeningRepository.findById(screeningId).orElseThrow( () -> new ResponseStatusException((HttpStatus.NOT_FOUND), "Screening's ID not found"));
        ticket.setCustomer(customer);
        ticket.setScreening(screening);
        return new ResponseEntity<Ticket>(this.ticketRepository.save(ticket), HttpStatus.CREATED);
    }

}
