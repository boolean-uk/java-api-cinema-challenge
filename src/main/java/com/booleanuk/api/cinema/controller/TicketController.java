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
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ScreeningRepository screeningRepository;

    @GetMapping("customers/{customerId}/screenings/{screeningsId}")
    public List<Ticket> getTickets(@PathVariable int customerId, @PathVariable int screeningsId) {
        return this.ticketRepository.getByCustomerIdAndScreeningId(customerId, screeningsId);
    }

    @PostMapping("customers/{customerId}/screenings/{screeningId}")
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket, @PathVariable int customerId, @PathVariable int screeningId) {
        Customer customer = this.customerRepository.findById(customerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sorry customer with this id does not exist, can't create a ticket"));
        ticket.setCustomer(customer);
        Screening screening = this.screeningRepository.findById(screeningId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sorry screening with this id does not exist, can't create a ticket"));
        ticket.setScreening(screening);

        ticket.setNumSeats(ticket.getNumSeats());

        return new ResponseEntity<>(this.ticketRepository.save(ticket), HttpStatus.CREATED);
    }
}