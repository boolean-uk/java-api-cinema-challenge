package com.booleanuk.api.cinema.Controller;

import com.booleanuk.api.cinema.Model.Customer;
import com.booleanuk.api.cinema.Model.Movie;
import com.booleanuk.api.cinema.Model.Screening;
import com.booleanuk.api.cinema.Model.Ticket;
import com.booleanuk.api.cinema.Repository.CustomerRepository;
import com.booleanuk.api.cinema.Repository.MovieRepository;
import com.booleanuk.api.cinema.Repository.ScreeningRepository;
import com.booleanuk.api.cinema.Repository.TicketRepository;
import com.booleanuk.api.cinema.Response.Error;
import com.booleanuk.api.cinema.Response.Response;
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
    private TicketRepository repository;
    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private CustomerRepository customerRepository;


    @GetMapping
    public ResponseEntity<Object> getAll() {
        List<Ticket> all = this.repository.findAll();
        return new ResponseEntity<Object>(new Response("success", all), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> create(
            @PathVariable ( name= "customerId") Integer customerId,
            @PathVariable ( name= "screeningId") Integer screeningId,
            @RequestBody Ticket ticket) {
        if (ticket.getNumSeats() == null) {
            return new ResponseEntity<Object>(new Response("error", new Error("bad request")), HttpStatus.BAD_REQUEST);
        }

        if (this.customerRepository.findById(customerId).isEmpty() || this.screeningRepository.findById(screeningId).isEmpty()) {
            return new ResponseEntity<Object>(new Response("error", new Error("not found")), HttpStatus.NOT_FOUND);
        }
        Customer customer = this.customerRepository.findById(customerId).get();
        Screening screening = this.screeningRepository.findById(screeningId).get();


        ticket.setTime();
        ticket.setCustomer(customer);
        ticket.setScreening(screening);
        ticket = this.repository.save(ticket);

        return new ResponseEntity<Object>(new Response("success", ticket), HttpStatus.CREATED);
    }
}
