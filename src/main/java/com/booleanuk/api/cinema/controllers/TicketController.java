package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Ticket;
import com.booleanuk.api.cinema.repositories.TicketRepository;
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

    @PostMapping
    public ResponseEntity<Ticket> create(@RequestBody Ticket ticket) {
        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setUpdatedAt(LocalDateTime.now());
        return new ResponseEntity<>(this.ticketRepository.save(ticket), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Ticket> getAll() {
        return this.ticketRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Ticket> getOne(@PathVariable int id) {
        Ticket ticket = this.ticketRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No ticket with that id")
        );
        return ResponseEntity.ok(ticket);
    }

    @PutMapping("{id}")
    public ResponseEntity<Ticket> update(@PathVariable int id, @RequestBody Ticket ticket) {
        Ticket ticketToUpdate = this.ticketRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No ticket with that id")
        );
        ticketToUpdate.setCustomerId(ticket.getCustomerId());
        ticketToUpdate.setScreeningId(ticket.getScreeningId());
        ticketToUpdate.setNumSeats(ticket.getNumSeats());
        ticketToUpdate.setUpdatedAt(LocalDateTime.now());
        return new ResponseEntity<>(this.ticketRepository.save(ticketToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Ticket> delete(@PathVariable int id) {
        Ticket ticketToDelete = this.ticketRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No ticket with that id")
        );
        this.ticketRepository.delete(ticketToDelete);
        return ResponseEntity.ok(ticketToDelete);
    }
}
