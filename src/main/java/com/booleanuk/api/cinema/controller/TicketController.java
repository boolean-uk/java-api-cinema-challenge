package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.payload.response.ErrorResponse;
import com.booleanuk.api.cinema.payload.response.Response;
import com.booleanuk.api.cinema.payload.response.TicketListResponse;
import com.booleanuk.api.cinema.payload.response.TicketResponse;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import com.booleanuk.api.cinema.views.Views;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/customers/{customerId}/screenings/{screeningId}")
public class TicketController {


    @Autowired
    private TicketRepository repository;

    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    @JsonView(Views.Detailed.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Response<?>> getAll(@PathVariable int customerId, @PathVariable int screeningId) {
        var tickets = this.repository.findAll().stream().filter(
                (a) -> a.getCustomerId() == customerId && a.getScreeningId() == screeningId
        ).toList();

        if (tickets.isEmpty())
            return new ResponseEntity<>(new ErrorResponse("no customer or tickets with those ids found"), HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(new TicketListResponse(tickets));
    }

    @PostMapping
    @JsonView(Views.Detailed.class)
    public ResponseEntity<Response<?>> PostUser(@PathVariable int  customerId, @PathVariable int screeningId,
                                               @RequestBody Ticket ticket) {
        ticket.setCustomerId(customerId);
        ticket.setScreeningId(screeningId);

        var screening = this.screeningRepository.findById(screeningId).orElse(null);
        if (screening == null)
            return new ResponseEntity<>(new ErrorResponse("not found"), HttpStatus.NOT_FOUND);

        var customer = this.customerRepository.findById(customerId).orElse(null);
        if (customer == null)
            return new ResponseEntity<>(new ErrorResponse("not found"), HttpStatus.NOT_FOUND);

        TicketResponse response = new TicketResponse();
        try {
            response.set(this.repository.save(ticket));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
