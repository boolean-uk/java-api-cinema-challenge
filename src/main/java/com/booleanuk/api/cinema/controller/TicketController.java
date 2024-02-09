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

import java.util.List;

@RestController
@RequestMapping("tickets")
public class TicketController{

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private ScreeningRepository screeningRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public List<Ticket> getAllTickets() {
        return this.ticketRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketByID(@PathVariable int id) {
        Ticket ticket = this.ticketRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found!"));
        return ResponseEntity.ok(ticket);
    }

    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
        Customer tempCus = this.customerRepository.findById(ticket.getScreening().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publisher found"));
        ticket.setCustomer(tempCus);
        Screening tempScr = this.screeningRepository.findById(ticket.getScreening().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No author found"));
        ticket.setScreening(tempScr);

        return ResponseEntity.ok(this.ticketRepository.save(ticket));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Ticket> deleteTicketByID(@PathVariable int id) {
        Ticket ticket = this.ticketRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found!"));
        this.ticketRepository.delete(ticket);
        return ResponseEntity.ok(ticket);


    }

    @PutMapping("/{id}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable int id, @RequestBody Ticket ticket) {

        Ticket ticketToUpdate = this.ticketRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found!"));
        Customer tempCus = this.customerRepository.findById(ticket.getCustomer().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No customer found"));
        ticket.setCustomer(tempCus);
        Screening tempScr = this.screeningRepository.findById(ticket.getScreening().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No screening found"));
        ticket.setScreening(tempScr);
        ticketToUpdate.setNumSeats(ticket.getNumSeats());
        ticketToUpdate.setCreatedAt(ticket.getCreatedAt());
        ticketToUpdate.setUpdatedAt(ticket.getUpdatedAt());

        return new ResponseEntity<>(this.ticketRepository.save(ticketToUpdate), HttpStatus.CREATED);
    }

}
