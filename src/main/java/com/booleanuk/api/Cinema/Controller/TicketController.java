package com.booleanuk.api.Cinema.Controller;

import com.booleanuk.api.Cinema.Model.Customer;
import com.booleanuk.api.Cinema.Model.Screening;
import com.booleanuk.api.Cinema.Model.Ticket;
import com.booleanuk.api.Cinema.Repository.CustomerRepository;
import com.booleanuk.api.Cinema.Repository.ScreeningRepository;
import com.booleanuk.api.Cinema.Repository.TicketRepository;
import org.hibernate.mapping.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @GetMapping("/customers/{customerId}/screenings/{screeningId}")
    public List<Ticket> getAllTickets(@PathVariable int id) {
        return this.ticketRepository.findByTicketId(id);
    }

    @PostMapping("/customers/{customerId}/screenings/{screeningId}")
    public ResponseEntity<Ticket> createTicket(@PathVariable int customerId, @PathVariable int screeningId, @RequestBody Ticket ticket) {
        Customer customer = this.customerRepository.findById(customerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        Screening screening = this.screeningRepository.findById(screeningId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Screening is not in the system."));

        ticket.setCustomers(customer);
        ticket.setScreening(screening);

        Ticket createdTicket = ticketRepository.save(ticket);
        return new ResponseEntity<>(createdTicket, HttpStatus.CREATED);
    }

}
