package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Customer;
import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.models.Ticket;
import com.booleanuk.api.cinema.repositories.CustomerRepository;
import com.booleanuk.api.cinema.repositories.MovieRepository;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import com.booleanuk.api.cinema.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("customers/{customerId}/screenings/{screeningId}")
public class TicketController {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ScreeningRepository screeningRepository;

    @PostMapping
    public ResponseEntity<Ticket> create(@RequestBody Ticket ticket, @PathVariable int customerId, @PathVariable int screeningId) {
        System.out.println("Test ticket post 1");
        Customer customer = this.customerRepository.findById(customerId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No customer with that id")
        );
        System.out.println("Test ticket post 2");
        Screening screening = this.screeningRepository.findById(screeningId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No screening with that id")
        );
        ticket.setCustomer(customer);
        ticket.setScreening(screening);
        System.out.println("Test ticket post 3");
        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setUpdatedAt(LocalDateTime.now());
        System.out.println("Test ticket post 4");
        return new ResponseEntity<>(this.ticketRepository.save(ticket), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Ticket> getAllByCustomerAndScreeningId(@PathVariable int customerId, @PathVariable int screeningId) {
        Customer customer = this.customerRepository.findById(customerId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No customer with that id")
        );
        Screening screening = this.screeningRepository.findById(screeningId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No screening with that id")
        );
        return this.ticketRepository.findAll();
    }

/*
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

 */
}
