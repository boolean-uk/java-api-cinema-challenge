package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import com.booleanuk.api.cinema.responses.TicketListResponse;
import com.booleanuk.api.cinema.responses.TicketResponse;
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
    private TicketRepository tickets;

    @Autowired
    private CustomerRepository customers;

    @Autowired
    private ScreeningRepository screenings;

    @PostMapping
    public ResponseEntity<Ticket> create(@PathVariable(name = "customerId") int customerId,
                                         @PathVariable(name = "screeningId") int screeningId,
                                         @RequestBody Ticket ticket) {
        Customer customer = this.customers.findById(customerId)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "not found")
                );

        Screening screening = this.screenings.findById(customerId)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "not found")
                );

        TicketResponse ticketResponse = new TicketResponse();
        ticketResponse.set(this.tickets.save(ticket));

        return null;
    }

    @GetMapping
    public ResponseEntity<TicketListResponse> getAll() {

        TicketListResponse ticketListResponse = new TicketListResponse();
        this.tickets.findAllById()
        ticketListResponse.set(this.tickets.findById(1));

        return null;
    }


}
