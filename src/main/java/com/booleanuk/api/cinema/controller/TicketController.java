package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.TicketRepository;
import com.booleanuk.api.cinema.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping
    public ResponseEntity<Response<?>> getAllTickets() {
        List<Ticket> tickets = ticketRepository.findAll();
        return ResponseEntity.ok(new Response<>("Success", tickets));
    }

    @PostMapping
    public ResponseEntity<Response<?>> createTicket(@RequestBody Ticket ticket) {
        Ticket savedTicket = ticketRepository.save(ticket);
        return new ResponseEntity<>(new Response<>("Ticket created successfully", savedTicket), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateTicket(@PathVariable Long id, @RequestBody Ticket ticket) {
        Ticket existingTicket = ticketRepository.findById(Math.toIntExact(id)).orElse(null);
        if (existingTicket == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>("Ticket not found", null));
        } else {
            ticket.setId(Math.toIntExact(id));
            Ticket updatedTicket = ticketRepository.save(ticket);
            return ResponseEntity.ok(new Response<>("Ticket updated successfully", updatedTicket));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteTicket(@PathVariable Long id) {
        Ticket existingTicket = ticketRepository.findById(Math.toIntExact(id)).orElse(null);
        if (existingTicket == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>("Ticket not found", null));
        } else {
            ticketRepository.delete(existingTicket);
            return ResponseEntity.ok(new Response<>("Ticket deleted successfully", existingTicket));
        }
    }
}
