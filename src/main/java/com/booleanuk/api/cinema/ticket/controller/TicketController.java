package com.booleanuk.api.cinema.ticket.controller;

import com.booleanuk.api.cinema.response.ResponseInterface;
import com.booleanuk.api.cinema.screening.model.Screening;
import com.booleanuk.api.cinema.screening.model.ScreeningDTO;
import com.booleanuk.api.cinema.screening.repository.ScreeningRepository;
import com.booleanuk.api.cinema.ticket.model.Ticket;
import com.booleanuk.api.cinema.ticket.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import static com.booleanuk.api.cinema.response.ResponseFactory.*;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    @Autowired
    TicketRepository ticketRepository;

    @PostMapping
    public ResponseEntity<ResponseInterface> bookTicket(@RequestBody Ticket ticket) {
        try {
            return CreatedSuccessResponse(ticketRepository.save(ticket));
        } catch (Exception e) {
            return NotFoundErrorResponse();
        }
    }

    @GetMapping
    public ResponseEntity<ResponseInterface> getAllTickets() {
        return OkSuccessResponse(this.ticketRepository.findAll());
    }
}
