package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import com.booleanuk.api.cinema.responses.ErrorResponse;
import com.booleanuk.api.cinema.responses.Response;
import com.booleanuk.api.cinema.responses.TicketListResponse;
import com.booleanuk.api.cinema.responses.TicketResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
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
    public ResponseEntity<Response<?>> create(@PathVariable(name = "customerId") int customerId,
                                              @PathVariable(name = "screeningId") int screeningId,
                                              @RequestBody Ticket ticket) {
        Customer customer = this.customers.findById(customerId)
                .orElse(
                        null
                );

        Screening screening = this.screenings.findById(screeningId)
                .orElse(
                        null
                );

        if (customer == null || screening == null) {
            ErrorResponse error = new ErrorResponse("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setUpdatedAt(LocalDateTime.now());
        ticket.setCustomer(customer);
        ticket.setScreening(screening);

        TicketResponse ticketResponse = new TicketResponse();
        ticketResponse.set(this.tickets.save(ticket));

        return new ResponseEntity<>(ticketResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Response<?>> getAll(@PathVariable(name = "customerId") int customerId,
                                              @PathVariable(name = "screeningId") int screeningId) {
        Customer customer = this.customers.findById(customerId)
                .orElse(
                        null
                );

        Screening screening = this.screenings.findById(screeningId)
                .orElse(
                        null
                );

        if (customer == null || screening == null) {
            ErrorResponse error = new ErrorResponse("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        List<Ticket> toReturn = this.tickets.findAllByCustomerIdAndScreeningId(customerId, screeningId);
        TicketListResponse ticketListResponse = new TicketListResponse();
        ticketListResponse.set(toReturn);

        return new ResponseEntity<>(ticketListResponse, HttpStatus.OK);
    }


}
