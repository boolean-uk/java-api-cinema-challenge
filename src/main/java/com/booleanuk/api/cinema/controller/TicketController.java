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
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("customers/{customerId}/screenings/{screeningId}")
    public ResponseEntity<ResponseGeneric<?>> bookATicket(@PathVariable  int customerId, @PathVariable int screeningId, @RequestBody Ticket ticket) {


        Customer customer = this.getACustomer(customerId);
        Screening screening = this.getAScreening(screeningId);
        if (customer == null || screening == null){
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        ticket.setCustomer(customer);
        ticket.setScreening(screening);
        Ticket savedTicket = this.ticketRepository.save(ticket);



        TicketResponse ticketResponse = new TicketResponse();
        ticketResponse.set(savedTicket);

        return new ResponseEntity<>(ticketResponse, HttpStatus.CREATED);
    }

    @GetMapping("customers/{customerId}/screenings/{screeningId}")
    public ResponseEntity<ResponseGeneric<?>> getAllTicket(@PathVariable  int customerId, @PathVariable int screeningId){
        Customer customer = this.getACustomer(customerId);
        Screening screening = this.getAScreening(screeningId);
        if (customer == null || screening == null){
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        List<Ticket> tickets = ticketRepository.findAll();
        TicketListResponse ticketListResponse = new TicketListResponse();
        ticketListResponse.set(tickets);
        return ResponseEntity.ok(ticketListResponse);

    }
    private Screening getAScreening(int id){
        return this.screeningRepository.findById(id).orElse(null);
    }
    private Customer getACustomer(int id){
        return this.customerRepository.findById(id).orElse(null);
    }


}
