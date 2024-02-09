package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("customers")
public class TicketController{

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private ScreeningRepository screeningRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/{customerId}/screenings/{screeningId}")
    public ResponseEntity<?> getAllTickets(@PathVariable int customerId, @PathVariable int screeningId) {
        List<Ticket> tickets = this.ticketRepository.findByCustomerIdAndScreeningId(customerId, screeningId);

        if (tickets.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No tickets found for the given customer and screening.");
        } else {
            return ResponseEntity.ok(tickets);
        }
    }




   /* @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketByID(@PathVariable int id) {
        Ticket ticket = this.ticketRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found!"));
        return ResponseEntity.ok(ticket);
    }*/

    @PostMapping("/{customerId}/screenings/{screeningId}")
    public ResponseEntity<Ticket> createTicket(@PathVariable int customerId, @PathVariable int screeningId, @RequestBody Ticket ticket) {
        Customer tempCus = this.customerRepository.findById(customerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publisher found"));
        ticket.setCustomer(tempCus);
        Screening tempScr = this.screeningRepository.findById(screeningId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No author found"));
        ticket.setScreening(tempScr);

        return ResponseEntity.ok(this.ticketRepository.save(ticket));
    }

}
