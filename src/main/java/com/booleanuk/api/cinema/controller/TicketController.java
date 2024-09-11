package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("tickets")
public class TicketController {

    @Autowired
    TicketRepository repository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    MovieRepository movieRepository;

    @GetMapping
    public ResponseEntity<List<Ticket>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Ticket> getById(@PathVariable Integer id){
        return ResponseEntity.ok(getTicketById(id));
    }

    @PostMapping("{id}")
    public ResponseEntity<Ticket> create(@PathVariable Integer customer_id, @RequestBody Ticket ticket) {
        Customer customer = customerRepository.findById(customer_id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer with given ID cannot be found"));
        Movie movie = movieRepository.findById(ticket.getMovie().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie with given ID cannot be found"));

        ticket.setCustomer(customer);
        ticket.setMovie(movie);

        return new ResponseEntity<>(this.repository.save(ticket), HttpStatus.CREATED);
    }


    private Ticket getTicketById(Integer id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Ticket with given ID cannot be found"
                ));
    }

}
