package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Customer;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.models.Ticket;
import com.booleanuk.api.cinema.repositories.CustomerRepository;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import com.booleanuk.api.cinema.repositories.TicketRepository;
import com.booleanuk.api.cinema.responses.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customers/{customerId}/screenings/{screeningId}")
public class TicketController {
    @Autowired
    TicketRepository repository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ScreeningRepository screeningRepository;

    @PostMapping
    public ResponseEntity<Response<?>> create(
            @PathVariable int customerId,
            @PathVariable int screeningId,
            @RequestBody Ticket ticket) {

        Customer customer = this.customerRepository.findById(customerId).orElse(null);
        Screening screening = this.screeningRepository.findById(screeningId).orElse(null);
        if (customer == null || screening == null) {
            // TODO: Could improve ux with better error message / more specific
            ErrorResponse error = new ErrorResponse();
            error.set("Invalid customer or screening id");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        ticket.setCustomer(customer);
        ticket.setScreening(screening);

        try {
            this.repository.save(ticket);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Could not create a new ticket, please check all fields are correct.");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        TicketResponse response = new TicketResponse();
        response.set(ticket);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Response<?>> getAll(
            @PathVariable int customerId,
            @PathVariable int screeningId
    ) {
        TicketListResponse response = new TicketListResponse();
        List<Ticket> tickets = this.repository.findAllByCustomerIdAndScreeningId(customerId, screeningId);
        if (tickets.isEmpty()) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No tickets found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        response.set(tickets);
        return ResponseEntity.ok(response);
    }
}
