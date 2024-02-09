package com.booleanuk.api.cinema.controller;


import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("tickets")
public class TicketController {
    @Autowired
    private TicketRepository ticketRepository;
    private LocalDateTime time = LocalDateTime.now();

    @GetMapping
    public List<Ticket> getAllTickets() {
        return this.ticketRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getATicket(@PathVariable int id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        return ResponseEntity.ok(ticket);
    }

    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
        ticket.setCreatedAt(time);
        ticket.setUpdatedAt(time);
        Ticket newTicket = this.ticketRepository.save(ticket);
        return new ResponseEntity<>(ticket, HttpStatus.CREATED);
    }


}
