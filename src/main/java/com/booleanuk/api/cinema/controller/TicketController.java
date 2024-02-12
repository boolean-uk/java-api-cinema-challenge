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

import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("customers/{customerId}/screenings/{screeningId}")
public class TicketController {
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ScreeningRepository screeningRepository;

    @GetMapping
    public List<Ticket> getAllTickets(@PathVariable int customerId, @PathVariable int screeningId) {
        this.findCustomer(customerId);
        this.findScreening(screeningId);
        return this.ticketRepository.findAll().stream().filter(ticket -> ticket.getCustomer().getId() == customerId &&
                ticket.getScreening().getId() == screeningId).toList();
    }

    @PostMapping
    public ResponseEntity<Ticket> bookTicket(@PathVariable int customerId, @PathVariable int screeningId, @RequestBody Ticket ticket) {
        ticket.setCustomer(findCustomer(customerId));
        ticket.setScreening(findScreening(screeningId));
        this.checkCapacityOfScreening(screeningId, ticket.getNumSeats());
        ticket.setCreatedAt(ZonedDateTime.now());
        ticket.setUpdatedAt(ZonedDateTime.now());
        return new ResponseEntity<>(this.ticketRepository.save(ticket), HttpStatus.CREATED);
    }

    // Checks if customerId exists or throws 404
    private Customer findCustomer(int customerId) {
        return this.customerRepository.findById(customerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No customer with that id found."));
    }

    // Checks if screeningId exists or throws 404
    private Screening findScreening(int screeningId) {
        return this.screeningRepository.findById(screeningId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No screening with that id found."));
    }

    // Check capacity for screening
    private void checkCapacityOfScreening(int screeningId, int numSeats) {
        Screening screening = this.screeningRepository.findById(screeningId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No screening with that id found."));
        int maxCapacity = screening.getCapacity();
        int ticketsSold = this.ticketRepository.findAll().stream().filter(ticket -> ticket.getScreening().getId() == screeningId).mapToInt(Ticket::getNumSeats).sum();
        // Throws exception if number of available seats is smaller than the requested amount
        if (numSeats > (maxCapacity - ticketsSold)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Number of seats are too large.");
        }
    }
}
