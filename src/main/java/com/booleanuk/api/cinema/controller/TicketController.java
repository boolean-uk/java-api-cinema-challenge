package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import com.booleanuk.api.cinema.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Response<?>> getTickets(@PathVariable int customerId, @PathVariable int screeningsId) {
        TicketListResponse ticketListResponse = new TicketListResponse();

        List<Ticket> tickets = this.ticketRepository.getByCustomerIdAndScreeningId(customerId, screeningsId);

        if (tickets.isEmpty()) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        ticketListResponse.set(tickets);
        return ResponseEntity.ok(ticketListResponse);
    }

    @PostMapping("customers/{customerId}/screenings/{screeningId}")
    public ResponseEntity<Response<?>> createTicket(@RequestBody Ticket ticket, @PathVariable int customerId, @PathVariable int screeningId) {
        TicketResponse ticketResponse = new TicketResponse();
        Customer customer = this.customerRepository.findById(customerId).orElse(null);
        Screening screening = this.screeningRepository.findById(screeningId).orElse(null);

        if (customer == null || screening == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        ticket.setCustomer(customer);
        ticket.setScreening(screening);

        if (ticket.getNumSeats() == 0) {
            ErrorResponse error = new ErrorResponse();
            error.set("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        try {
            ticketResponse.set(this.ticketRepository.save(ticket));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ticketResponse, HttpStatus.CREATED);
    }
}
