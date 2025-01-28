package com.booleanuk.api.cinema.tickets;

import com.booleanuk.api.cinema.customers.Customer;
import com.booleanuk.api.cinema.customers.CustomerRepository;
import com.booleanuk.api.cinema.screenings.Screening;
import com.booleanuk.api.cinema.screenings.ScreeningRepository;
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
    private ScreeningRepository screeningRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets(@PathVariable int customerId, @PathVariable int screeningId) {
        Customer customer = this.customerRepository.findById(customerId).orElse(null);

        Screening screening = this.screeningRepository.findById(screeningId).orElse(null);

        if (customer == null || screening == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No ticket found for the customer and screening with those ids");
        }

        List<Ticket> tickets = this.ticketRepository.findByCustomerAndScreening(customer, screening); //findALL?
        return ResponseEntity.ok(tickets);
    }

    @PostMapping
    public ResponseEntity<Ticket> createNewTicket(@PathVariable int customerId, @PathVariable int screeningId, @RequestBody Ticket ticket) {
        Customer customer = this.customerRepository.findById(customerId).orElse(null);

        Screening screening = this.screeningRepository.findById(screeningId).orElse(null);

        if (customer == null || screening == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No customer or screening with those ids found");
        }

        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setUpdatedAt(LocalDateTime.now());
        ticket.setCustomer(customer);
        ticket.setScreening(screening);
        return new ResponseEntity<>(this.ticketRepository.save(ticket), HttpStatus.CREATED);
    }

    @DeleteMapping("{ticket_id}")
    public ResponseEntity<Ticket> deleteTicket(@PathVariable int id) {
        Ticket ticketToDelete = this.ticketRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No ticket with that id was found")
        );
        this.ticketRepository.delete(ticketToDelete);
        return ResponseEntity.ok(ticketToDelete);
    }

}