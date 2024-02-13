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

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("tickets")
public class TicketController {


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
        Ticket ticket = this.ticketRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket Not Found by ID"));
        return ResponseEntity.ok(ticket);
    }


    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {

        Customer ticketCustomer = customerRepository.findById(ticket.getCustomer().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer Not Found by ID"));

        Screening ticketScreening = screeningRepository.findById(ticket.getScreening().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Screening Not Found by ID"));


        ticket.setCustomer(ticketCustomer);
        ticket.setScreening(ticketScreening);
        return ResponseEntity.ok(ticketRepository.save(ticket));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable int id, @RequestBody Ticket ticket) {

        Ticket ticketToUpdate = ticketRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket Not Found by ID"));
        Customer customer = customerRepository.findById(ticket.getCustomer().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer Not Found by ID"));
        Screening screening = screeningRepository.findById(ticket.getScreening().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Screening Not Found by ID"));

        ticketToUpdate.setCustomer(customer);
        ticketToUpdate.setScreening(screening);

        return new ResponseEntity<Ticket>(ticketRepository.save(ticketToUpdate), HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Ticket> deleteTicket(@PathVariable int id) {
        Ticket ticketToDelete = this.ticketRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket Not Found by ID"));
        this.ticketRepository.delete(ticketToDelete);

        return ResponseEntity.ok(ticketToDelete);
    }

}
