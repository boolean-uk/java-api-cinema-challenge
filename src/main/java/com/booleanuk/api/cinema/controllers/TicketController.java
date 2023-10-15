package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.CustomResponse;
import com.booleanuk.api.cinema.models.Ticket;
import com.booleanuk.api.cinema.repositories.CustomerRepository;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import com.booleanuk.api.cinema.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/customers/{customerId}/screenings/{screeningId}")
public class TicketController {
    @Autowired
    private TicketRepository repo;

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private ScreeningRepository screeningRepo;

    @GetMapping
    public ResponseEntity<CustomResponse<List<Ticket>>> getAll(@PathVariable(name = "customerId") int customerId, @PathVariable(name = "screeningId") int screeningId) {
        customerRepo.findById(customerId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "No customers were found with that id"
                        )
                );

        screeningRepo.findById(screeningId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "No screenings were found with that id"
                        )
                );

        return new ResponseEntity<>(
                new CustomResponse<>(repo.findByCustomerIdAndScreeningId(customerId, screeningId).get()),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<CustomResponse<Ticket>> createOne(@PathVariable(name = "customerId") int customerId, @PathVariable(name = "screeningId") int screeningId, @RequestBody Ticket ticket) {
        ticket.setCustomer(
                customerRepo.findById(customerId)
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "No customers were found with that id"
                                )
                        )
        );

        ticket.setScreening(
                screeningRepo.findById(screeningId)
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "No screenings were found with that id"
                                )
                        )
        );

        return new ResponseEntity<>(
                new CustomResponse<>(repo.save(ticket)),
                HttpStatus.CREATED
        );
    }
}
