package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Customer;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.models.Ticket;
import com.booleanuk.api.cinema.repositories.CustomerRepository;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import com.booleanuk.api.cinema.repositories.TicketRepository;
import com.booleanuk.api.cinema.responses.ApiResponse;
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
    public ResponseEntity<ApiResponse<Ticket>> createTicket(@RequestBody Ticket ticket, @PathVariable("customerId") int customerId, @PathVariable("screeningId") int screeningId) {
        Screening screening = screeningRepository.findById(screeningId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No screening with provided id found."));
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No customer with provided id found."));

        Ticket newTicket = new Ticket();
        newTicket.setCustomer(customer);
        newTicket.setScreening(screening);
        newTicket.setNumSeats(ticket.getNumSeats());

        Ticket createdTicket = ticketRepository.save(newTicket);
        ApiResponse<Ticket> response = new ApiResponse<>("success", createdTicket);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Ticket>>> getAll(@PathVariable("customerId") int customerId, @PathVariable("screeningId") int screeningId) {
        List<Ticket> tickets = ticketRepository.findByCustomerIdAndScreeningId(customerId, screeningId);
        ApiResponse<List<Ticket>> response = new ApiResponse<>("success", tickets);
        return ResponseEntity.ok(response);
    }
}