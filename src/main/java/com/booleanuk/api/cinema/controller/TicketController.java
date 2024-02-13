package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import com.booleanuk.api.cinema.response.ErrorResponse;
import com.booleanuk.api.cinema.response.Response;
import com.booleanuk.api.cinema.response.TicketListResponse;
import com.booleanuk.api.cinema.response.TicketResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
public class TicketController {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ScreeningRepository screeningRepository;

    @GetMapping("customers/{customerID}/screenings/{screeningID}")
    public ResponseEntity<Response<?>> getAllTicket(@PathVariable int customerID, @PathVariable int screeningID){
        Customer customer = this.customerRepository.findById(customerID).orElse(null);
        if (customer == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No customer with that id found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        Screening screening = this.screeningRepository.findById(screeningID).orElse(null);
        if (screening == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No screening with that id found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        List<Ticket> tickets = this.ticketRepository.findByCustomerIdAndScreeningId(customerID, screeningID);
        if (tickets.isEmpty()) {
            ErrorResponse error = new ErrorResponse();
            error.set("No ticket found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        TicketListResponse ticketListResponse = new TicketListResponse();
        ticketListResponse.set(tickets);
        return ResponseEntity.ok(ticketListResponse);
    }

    @PostMapping("customers/{customerID}/screenings/{screeningID}")
    public ResponseEntity<Response<?>> addTicket(@PathVariable int customerID, @PathVariable int screeningID, @RequestBody Ticket ticket){
        Customer customer = this.customerRepository.findById(customerID).orElse(null);
        if (customer == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No customer with that id found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        Screening screening = this.screeningRepository.findById(screeningID).orElse(null);
        if (screening == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No screening with that id found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        Ticket ticket1 = new Ticket();
        try {
            ticket1.setCustomer(customer);
            ticket1.setScreening(screening);
            ticket1.setNumSeats(ticket.getNumSeats());
            ticket1.setUpdatedAt(ZonedDateTime.now());
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Could not create ticket for that screening and customer");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        this.ticketRepository.save(ticket1);
        TicketResponse ticketResponse = new TicketResponse();
        ticketResponse.set(ticket1);
        return new ResponseEntity<>(ticketResponse, HttpStatus.CREATED);
    }
}
