package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Customer;
import com.booleanuk.api.cinema.models.responses.Response;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.models.Ticket;
import com.booleanuk.api.cinema.repositories.CustomerRepository;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import com.booleanuk.api.cinema.repositories.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("customers/{customerId}/screenings/{screeningId}")
@AllArgsConstructor
public class TicketController {
    private final CustomerRepository customerRepository;
    private final ScreeningRepository screeningRepository;
    private final TicketRepository ticketRepository;

    @PostMapping
    public ResponseEntity<Response<?>> add(@PathVariable int customerId, @PathVariable int screeningId, @RequestBody Ticket ticket) {
        if (this.screeningRepository.findById(screeningId).isEmpty() || this.customerRepository.findById(customerId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.NOT_FOUND);
        }

        Customer customer = this.customerRepository.findById(customerId).get();
        Screening screening = this.screeningRepository.findById(screeningId).get();

        ticket.setCustomer(customer);
        ticket.setScreening(screening);

        return ResponseEntity.status(HttpStatus.CREATED).body(Response.success(this.ticketRepository.save(ticket)));
    }

    @GetMapping
    public ResponseEntity<Response<?>> getAll(@PathVariable int customerId, @PathVariable int screeningId) {
        if (this.screeningRepository.findById(screeningId).isEmpty() || this.customerRepository.findById(customerId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.NOT_FOUND);
        }

        return ResponseEntity.ok(Response
                .success(this.ticketRepository.findAllByCustomerIdAndScreeningId(customerId, screeningId)));
    }
}
