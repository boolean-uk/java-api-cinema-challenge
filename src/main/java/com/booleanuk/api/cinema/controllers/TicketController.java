package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.models.Ticket;
import com.booleanuk.api.cinema.models.User;
import com.booleanuk.api.cinema.payload.response.ErrorResponse;
import com.booleanuk.api.cinema.payload.response.Response;
import com.booleanuk.api.cinema.payload.response.TicketListResponse;
import com.booleanuk.api.cinema.payload.response.TicketResponse;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import com.booleanuk.api.cinema.repository.UserRepository;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("customers/{customerId}/screenings/{screeningId}")
public class TicketController {
    @Autowired
    TicketRepository repo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    @JsonIgnoreProperties({"tickets"})
    ScreeningRepository screeningRepo;

    @PostMapping
    public ResponseEntity<Response<?>> addOne(@PathVariable int customerId, @PathVariable int screeningId, @RequestBody Ticket ticket){
        User user = this.userRepo.findById(customerId).orElse(null);
        Screening screening = this.screeningRepo.findById(screeningId).orElse(null);

        if(user == null || screening == null){
            ErrorResponse error = new ErrorResponse();
            error.set("Not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        ticket.setScreening(screening);
        ticket.setCustomer(user);
        ticket.setCreated(OffsetDateTime.now());
        ticket.setUpdatedAt(OffsetDateTime.now());

        this.repo.save(ticket);
        TicketResponse resp = new TicketResponse();
        resp.set(ticket);

        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Response<?>> getAll(@PathVariable int customerId, @PathVariable int screeningId){
        User user = this.userRepo.findById(customerId).orElse(null);
        Screening screening = this.screeningRepo.findById(screeningId).orElse(null);

        if(user == null || screening == null){
            ErrorResponse error = new ErrorResponse();
            error.set("Ticket not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        List<Ticket> tickets = this.repo.findTicketsByCustomerAndScreening(user, screening);

        TicketListResponse resp = new TicketListResponse();
        resp.set(tickets);

        return ResponseEntity.ok(resp);
    }


}
