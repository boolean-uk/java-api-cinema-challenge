package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ScreeningRepository screeningRepository;


    @GetMapping("/tickets")
    public List<Ticket> getAllTickets() {
        return this.ticketRepository.findAll();
    }

    @PostMapping("customers/{customerId}/screenings/{screeningId}")
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket, @PathVariable ("customerId") int customerId, @PathVariable("screeningId") int screeningId ) {
        Customer tempCustomer = this.customerRepository.findById(customerId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not create ticket if customer id is not found"));
        ticket.setCustomer(tempCustomer);
        Screening tempScreening = this.screeningRepository.findById(screeningId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not create ticket if screening is not found."));
        ticket.setScreening(tempScreening);

        LocalDateTime createdAt = LocalDateTime.now();
        ticket.setCreatedAt(createdAt);
        ticket.setUpdatedAt(createdAt);
        return new ResponseEntity<Ticket>(this.ticketRepository.save(ticket), HttpStatus.CREATED);

    }
//    @GetMapping ("movies/{id}/screenings")
//    public List<Screening> getAll(@PathVariable int movie_id) {
//        List<Screening> screenings = this.screeningRepository.getScreeningByMovieId(movie_id);
//        return screenings;
//    }



}
