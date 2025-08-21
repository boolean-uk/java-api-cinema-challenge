package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Customer;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.models.Ticket;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import com.booleanuk.api.cinema.payload.response.TicketListResponse;
import com.booleanuk.api.cinema.payload.response.TicketResponse;
import com.booleanuk.api.cinema.payload.response.ErrorResponse;
import com.booleanuk.api.cinema.payload.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("tickets")
public class TicketController {
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ScreeningRepository screeningRepository;

    @GetMapping
    public ResponseEntity<TicketListResponse> getAllTickets() {
        TicketListResponse ticketListResponse = new TicketListResponse();
        ticketListResponse.set(this.ticketRepository.findAll());
        return ResponseEntity.ok(ticketListResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createTicket(@RequestBody Ticket ticket) {
        TicketResponse ticketResponse = new TicketResponse();
        try {
            ticketResponse.set(this.ticketRepository.save(ticket));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ticketResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getTicketById(@PathVariable int id) {
        Ticket ticket = this.ticketRepository.findById(id).orElse(null);
        if (ticket == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        TicketResponse ticketResponse = new TicketResponse();
        ticketResponse.set(ticket);
        return ResponseEntity.ok(ticketResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateTicket(@PathVariable int id, @RequestBody Ticket ticket) {
        Screening screening = screeningRepository.findById(ticket.getScreening_id()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        Customer customer = customerRepository.findById(ticket.getCustomer_id()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        Ticket ticketToUpdate = this.ticketRepository.findById(id).orElse(null);

        if (ticketToUpdate == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        ticketToUpdate.setCustomer(customer);
        ticketToUpdate.setScreening(screening);
        ticketToUpdate.setNumSeats(ticket.getNumSeats());

        try {
            ticketToUpdate = this.ticketRepository.save(ticketToUpdate);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        TicketResponse ticketResponse = new TicketResponse();
        ticketResponse.set(ticketToUpdate);
        return new ResponseEntity<>(ticketResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteTicket(@PathVariable int id) {
        Ticket ticketToDelete = this.ticketRepository.findById(id).orElse(null);
        if (ticketToDelete == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        this.ticketRepository.delete(ticketToDelete);
        TicketResponse ticketResponse = new TicketResponse();
        ticketResponse.set(ticketToDelete);
        return ResponseEntity.ok(ticketResponse);
    }
}
