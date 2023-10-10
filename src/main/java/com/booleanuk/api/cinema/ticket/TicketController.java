package com.booleanuk.api.cinema.ticket;

import com.booleanuk.api.cinema.movie.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("tickets")
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping
    public List<Ticket> getAllMovies() {
        return this.ticketRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Ticket> bookTicket(@RequestBody Ticket ticket) {
        if (ticket.getNumSeats() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The number of seats are 0");
        }

        Date today = new Date();
        ticket.setCreatedAt(today);
        ticket.setUpdatedAt(today);

        return new ResponseEntity<>(this.ticketRepository.save(ticket), HttpStatus.CREATED);
    }
}
