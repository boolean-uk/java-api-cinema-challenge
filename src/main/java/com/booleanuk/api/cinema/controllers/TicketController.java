package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Customer;
import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.models.Ticket;
import com.booleanuk.api.cinema.repositories.CustomerRepository;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import com.booleanuk.api.cinema.repositories.TicketRepository;
import com.booleanuk.api.cinema.respons_handling.Message;
import com.booleanuk.api.cinema.respons_handling.ResponseCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @GetMapping
    public ResponseEntity<ResponseCreator<?>> getAll(@PathVariable("customerId") Integer customerId, @PathVariable("screeningId") Integer screeningId) {
        Screening screening = this.screeningRepository.findById(screeningId).orElse(null);
        if (screening == null) {
            return new ResponseEntity<>(new ResponseCreator<>("error", new Message("Screening not found")) , HttpStatus.NOT_FOUND);
        }

        Customer customer = this.customerRepository.findById(customerId).orElse(null);
        if (customer == null) {
            return new ResponseEntity<>(new ResponseCreator<>("error", new Message("Customer not found")) , HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(new ResponseCreator<>("success", this.ticketRepository.findByCustomerAndScreening(customer, screening)));
    }

    @PostMapping
    public ResponseEntity<ResponseCreator<?>> createTicket(@PathVariable("customerId") Integer customerId, @PathVariable("screeningId") Integer screeningId, @RequestBody Ticket ticket) {
        Screening screening = this.screeningRepository.findById(screeningId).orElse(null);
        if (screening == null) {
            return new ResponseEntity<>(new ResponseCreator<>("error", new Message("Screening not found")) , HttpStatus.NOT_FOUND);
        }

        Customer customer = this.customerRepository.findById(customerId).orElse(null);
        if (customer == null) {
            return new ResponseEntity<>(new ResponseCreator<>("error", new Message("Customer not found")) , HttpStatus.NOT_FOUND);
        }

        //Check that maximum capacity is not exceeded
        int ticketsRemaining = screening.getCapacity() - ticket.getNumSeats();
        for (Ticket ticketToCount : this.ticketRepository.findByScreening(screening)) {
            ticketsRemaining -= ticketToCount.getNumSeats();
        }

        if (ticketsRemaining < 0) {
            return new ResponseEntity<>(new ResponseCreator<>("error", new Message("Screening is over booked")) , HttpStatus.BAD_REQUEST);
        }
        ticket.setScreening(screening);
        ticket.setCustomer(customer);
        try {
            this.ticketRepository.save(ticket);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseCreator<>("error", new Message("At least one non-null field is null")) , HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseCreator<>("success", ticket), HttpStatus.CREATED);
    }
}
