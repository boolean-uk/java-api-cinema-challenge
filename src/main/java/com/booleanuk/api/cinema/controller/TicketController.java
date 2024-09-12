package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.repository.MovieRepository;
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

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("customers/{id}/screenings")
public class TicketController {

    @Autowired
    TicketRepository repository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ScreeningRepository screeningRepository;

    @GetMapping
    public ResponseEntity<List<Ticket>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("{screeningId}")
    public ResponseEntity<Response<?>> getTicketsByScreeningId(@PathVariable(name = "screeningId") Integer id){
        Screening screening = screeningRepository.findById(id).orElse(null);
        if(screening == null || screening.getTickets().isEmpty()) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        List<Ticket> tickets = screening.getTickets();

        TicketListResponse ticketListResponse = new TicketListResponse();
        ticketListResponse.set(tickets);

        return ResponseEntity.ok(ticketListResponse);
    }


    @PostMapping("{screeningId}")
    public ResponseEntity<?> createTicket(@PathVariable(name = "id") Integer id, @PathVariable(name = "screeningId") Integer screeningId, @RequestBody Ticket ticket) {
        Customer customer = this.customerRepository.findById(id).orElse(null);
        Screening screening = this.screeningRepository.findById(screeningId).orElse(null);

        if(customer == null || screening == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        ticket.setCreatedAt(new Date());
        ticket.setCustomer(customer);
        ticket.setScreening(screening);

        screening.addTicket(ticket);
        customer.addTicket(ticket);

        TicketResponse ticketResponse = new TicketResponse();
        ticketResponse.set(ticket);
        this.repository.save(ticket);

        return new ResponseEntity<>(ticketResponse, HttpStatus.CREATED);
    }
}
