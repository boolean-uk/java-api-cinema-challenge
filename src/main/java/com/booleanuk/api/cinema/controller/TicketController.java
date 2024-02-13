package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import com.booleanuk.api.cinema.response.*;
import com.booleanuk.api.cinema.response.ticket.TicketListResponse;
import com.booleanuk.api.cinema.response.ticket.TicketResponse;
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
    private ScreeningRepository screeningRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/customers/{customerId}/screenings/{screeningId}")
    public ResponseEntity<Response<?>> getAllTicketsByCustomerForScreening(@PathVariable (name = "customerId") int customerId, @PathVariable  (name = "screeningId") int screeningId) {
        List<Ticket> ticketsByCustomerForScreening = ticketRepository.findByCustomer_IdAndScreening_Id(customerId, screeningId);
        if (ticketsByCustomerForScreening.isEmpty() || ticketsByCustomerForScreening == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No ticket found for the customer and screening with those ids found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        TicketListResponse ticketListResponse = new TicketListResponse();
        ticketListResponse.set(ticketsByCustomerForScreening);
        return ResponseEntity.ok(ticketListResponse);
    }

    @PostMapping("/customers/{customerId}/screenings/{screeningId}")
    public ResponseEntity<Response<?>> createTicketForCustomerAndScreening(@PathVariable (name = "customerId") int customerId, @PathVariable  (name = "screeningId") int screeningId, @RequestBody Ticket ticket) {
        if (ticket.getNumSeats() < 0) {
            ErrorResponse error = new ErrorResponse();
            error.set("Seats can't be negative");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        Customer tempCustomer= this.customerRepository.findById(customerId).orElse(null);
        if(tempCustomer == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No customers matching that id were found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        Screening tempScreening = this.screeningRepository.findById(screeningId).orElse(null);
        if(tempScreening == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No screenings matching that id were found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        ticket.setCustomer(tempCustomer);
        ticket.setScreening(tempScreening);

        Ticket createdTicket = this.ticketRepository.save(ticket);

        TicketResponse ticketResponse = new TicketResponse();
        ticketResponse.set(createdTicket);
        return new ResponseEntity<>(ticketResponse, HttpStatus.CREATED);
    }
}
