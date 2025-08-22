package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.models.Ticket;
import com.booleanuk.api.cinema.models.User;
import com.booleanuk.api.cinema.payload.response.ErrorResponse;
import com.booleanuk.api.cinema.payload.response.Response;
import com.booleanuk.api.cinema.payload.response.TicketListResponse;
import com.booleanuk.api.cinema.payload.response.TicketResponse;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import com.booleanuk.api.cinema.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class TicketController {
    @Autowired
    private UserRepository customerRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private ScreeningRepository screeningRepository;

    public record PostTicket(int numSeats) {}

    @PostMapping("/customers/{customerId}/screenings/{screeningId}")
    public ResponseEntity<Response<?>> createTicket(@PathVariable int customerId, @PathVariable int screeningId, @RequestBody PostTicket request){
        TicketResponse ticketResponse = new TicketResponse();
        User customer = customerRepository.findById(customerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find customer with that id."));
        Screening screening = screeningRepository.findById(screeningId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find screening with that id."));

        Ticket ticket = new Ticket(customer, screening, request.numSeats());
        try {
            ticketResponse.set(this.ticketRepository.save(ticket));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ticketResponse, HttpStatus.CREATED);
    }

    @GetMapping("/customers/{customerId}/screenings/{screeningId}")
    public ResponseEntity<TicketListResponse> getAllTickets(@PathVariable int customerId, @PathVariable int screeningId) {
        TicketListResponse ticketListResponse = new TicketListResponse();
        User customer = customerRepository.findById(customerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find customer with that id."));
        Screening screening = screeningRepository.findById(screeningId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find screening with that id."));
        ticketListResponse.set(this.ticketRepository.findByUserAndScreening(customer, screening));
        return ResponseEntity.ok(ticketListResponse);
    }
}
