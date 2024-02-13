package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import com.booleanuk.api.cinema.response.ErrorResponse;
import com.booleanuk.api.cinema.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/customers/{customerId}/screenings/{screeningId}")
public class TicketController {
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ScreeningRepository screeningRepository;

    @PostMapping
    public ResponseEntity<Response<?>> create(@PathVariable("customerId") int customerId, @PathVariable("screeningId") int screeningId, @RequestBody Ticket ticket) {

        Screening screening = this.screeningRepository.findById(screeningId).orElse(null);
        Customer customer= this.customerRepository.findById(customerId).orElse(null);
        if(screening == null || customer == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        ticket.setScreening(screening);
        ticket.setCustomer(customer);
        ticket.setCreatedAt(String.valueOf(LocalDateTime.now()));
        ticket.setUpdatedAt(customer.getCreatedAt());
        Response<Ticket> ticketResponse = new Response<>();
        ticketResponse.set(this.ticketRepository.save(ticket));
        return new ResponseEntity<>(ticketResponse, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<Response<?>> getAll(@PathVariable("screeningId") int screeningId, @PathVariable("customerId") int customerId) {
        Screening screening = this.screeningRepository.findById(screeningId).orElse(null);
        Customer customer= this.customerRepository.findById(customerId).orElse(null);
        if(screening == null || customer == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        List<Ticket> tickets = customer.getTickets();
        tickets.retainAll(screening.getTickets());
        Response<List<Ticket>> ticketResponse = new Response<>();
        ticketResponse.set(tickets);
        return ResponseEntity.ok(ticketResponse);
    }

}
