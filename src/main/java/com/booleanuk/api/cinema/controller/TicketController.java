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
@RequestMapping("/customers/{customerID}/screenings/{screeningID}")
public class TicketController {
    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    ScreeningRepository screeningRepository;

    @Autowired
    CustomerRepository customerRepository;

    @PostMapping
    public ResponseEntity<Ticket> createTicket(
            @PathVariable("customerID") int customerID,
            @PathVariable("screeningID") int screeningID,
            @RequestBody Ticket ticket) {

        Customer customer = customerRepository.findById(customerID).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Cannot create this screening, as the customer id could not be found"));
        Screening screening = screeningRepository.findById(screeningID).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Cannot create this ticket, as the screening id could not be found"));

        ticket.setCustomer(customer);
        ticket.setScreening(screening);

        return new ResponseEntity<Ticket>(this.ticketRepository.save(ticket), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Ticket> getAllTickets(@PathVariable("customerID") int customerID, @PathVariable("screeningID") int screeningID) {
        return this.ticketRepository.getTicketsByCustomerIdAndScreeningId(customerID, screeningID);
    }


}
