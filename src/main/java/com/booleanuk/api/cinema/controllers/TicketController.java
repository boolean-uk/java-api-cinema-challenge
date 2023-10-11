package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Customer;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.models.Ticket;
import com.booleanuk.api.cinema.repositories.CustomerRepository;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import com.booleanuk.api.cinema.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("customers/{customerId}/screenings/{screeningId}")
public class TicketController {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    ScreeningRepository screeningRepository;

    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets(@PathVariable(name = "customerID") int customerId,@PathVariable(name = "screeningID") int screeningId) {
        Customer customer = this.customerRepository.findById(customerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Customer Not Found"));
        Screening screening = this.screeningRepository.findById(screeningId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Screening Not Found"));
        List<Ticket> newList = new ArrayList<>();
        for (Ticket ticket: customer.getTicketList()){
            if (screening.getTicketList().contains(ticket)) {
                newList.add(ticket);
            }
        }
        return ResponseEntity.ok(newList);
    }

    @PostMapping
    public ResponseEntity<Ticket> addTicket(@PathVariable(name = "customerId") int customerId, @PathVariable(name = "screeningId") int screeningId, @RequestBody Ticket ticket) {
        Customer customer = this.customerRepository.findById(customerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Customer Not Found"));
        Screening screening = this.screeningRepository.findById(screeningId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Screening Not Found"));
        ticket.setCustomer(customer);
        ticket.setScreening(screening);
        ticket.setCreatedAt(java.time.LocalDateTime.now());
        customer.getTicketList().add(ticket);
        screening.getTicketList().add(ticket);
        return new ResponseEntity<>(this.ticketRepository.save(ticket),HttpStatus.CREATED);
    }

}
