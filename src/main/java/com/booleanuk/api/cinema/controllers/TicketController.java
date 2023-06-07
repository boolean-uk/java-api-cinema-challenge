package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Customer;
import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.models.Ticket;
import com.booleanuk.api.cinema.repositories.CustomerRepository;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import com.booleanuk.api.cinema.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("customers/{customerId}/screenings/{screeningId}")
public class TicketController {
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ScreeningRepository screeningRepository;


    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket, @PathVariable("customerId") int customerId, @PathVariable("screeningId") int screeningId) {
        Screening screening = null;
        Customer customer = null;
        screening = this.screeningRepository.findById(screeningId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No screening with provided id found."));
        customer = this.customerRepository.findById(customerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No customer with provided id found."));

        Ticket newTicket = new Ticket();
        newTicket.setCustomer(customer);
        newTicket.setScreening(screening);
        newTicket.setNumSeats(ticket.getNumSeats());

        return new ResponseEntity<Ticket>(this.ticketRepository.save(newTicket), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Ticket> getAll(@PathVariable("customerId") int customerId, @PathVariable("screeningId") int screeningId) {
        Screening screening = null;
        Customer customer = null;
        screening = this.screeningRepository.findById(screeningId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No screening with provided id found."));
        customer = this.customerRepository.findById(customerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No customer with provided id found."));

        return this.ticketRepository.findByCustomerIdAndScreeningId(customerId, screeningId);
    }


}
