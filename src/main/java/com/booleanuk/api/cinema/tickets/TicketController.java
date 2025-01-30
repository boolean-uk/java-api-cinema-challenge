package com.booleanuk.api.cinema.tickets;


import com.booleanuk.api.cinema.customers.Customer;
import com.booleanuk.api.cinema.customers.CustomerRepository;
import com.booleanuk.api.cinema.responses.ErrorResponse;
import com.booleanuk.api.cinema.responses.Response;
import com.booleanuk.api.cinema.screens.Screen;
import com.booleanuk.api.cinema.screens.ScreenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("customers")
public class TicketController {

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ScreenRepository screenRepository;

    @GetMapping("/{customerId}/screenings/{screeningId}")
    public ResponseEntity<Response<?>> getAllTickets(@PathVariable(name = "customerId") int customerId, @PathVariable(name = "screeningId") int screeningId) {

        Customer customer = this.customerRepository.findById(customerId).orElse(null);
        if (customer == null) {
            ErrorResponse error = new ErrorResponse("No such customer found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        // Check if the tickets belonging to the customer ID match the screenID
        List<Ticket> tickets = new ArrayList<>();
        for(Ticket customerTicket : customer.getTickets()) {
            if (customerTicket.getScreen().getId() == screeningId) {
                tickets.add(customerTicket);
            }
        }

        if (tickets.isEmpty()) {
            ErrorResponse noMatchingTicketsError = new ErrorResponse("The customer exists but does not have any tickets with that screening ID");
            return new ResponseEntity<>(noMatchingTicketsError, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new Response<>(tickets), HttpStatus.OK);

    }

    @PostMapping("/{customerId}/screenings/{screeningId}")
    public ResponseEntity<Response<?>> bookNewTicket(@PathVariable(name = "customerId") int customerId,
                                                     @PathVariable(name = "screeningId") int screeningId,
                                                     @RequestBody Ticket ticket)
    {

        Customer customer = this.customerRepository.findById(customerId).orElse(null);
        if (customer == null) {
            ErrorResponse error = new ErrorResponse("No such customer found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        Screen screen = this.screenRepository.findById(screeningId).orElse(null);
        if (screen == null) {
            ErrorResponse error = new ErrorResponse("Customer exists but no such screen found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        ticket.setCustomer(customer);
        ticket.setScreen(screen);
        ticket.setCreated_at(String.valueOf(LocalDateTime.now()));
        return new ResponseEntity<>(new Response<>(this.ticketRepository.save(ticket)), HttpStatus.CREATED);

    }
}
