package com.booleanuk.api.cinema.ticket;

import com.booleanuk.api.cinema.customer.Customer;
import com.booleanuk.api.cinema.customer.CustomerRepository;
import com.booleanuk.api.cinema.response.*;
import com.booleanuk.api.cinema.response.Error;
import com.booleanuk.api.cinema.screening.Screening;
import com.booleanuk.api.cinema.screening.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("customers")
public class TicketController {
    LocalDateTime currentTime = LocalDateTime.now();

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ScreeningRepository screeningRepository;

    @GetMapping("{customerId}/screenings/{screeningId}")
    public ResponseEntity<Response> getTicket(@PathVariable int customerId, @PathVariable int screeningId) {
        Customer customer = this.customerRepository
                .findById(customerId)
                .orElse(null);
        if (customer == null) {
            return new ResponseEntity<>(new ErrorResponse(new Error("Customer not found")), HttpStatus.NOT_FOUND);
        }

        Screening screening = this.screeningRepository
                .findById(screeningId)
                .orElse(null);
        if(screening == null) {
            return new ResponseEntity<>(new ErrorResponse(new Error("Screening not found")), HttpStatus.NOT_FOUND);
        }

        List<Ticket> tickets = ticketRepository.findByCustomerAndScreening(customer, screening);
        if (tickets.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No tickets found");
        }
        return new ResponseEntity<>(new TicketListResponse(tickets), HttpStatus.OK);
    }

    @PostMapping("{customerId}/screenings/{screeningId}")
    public ResponseEntity<Response> createTicket(@PathVariable int customerId, @PathVariable int screeningId, @RequestBody Ticket ticket) {
        Customer customer = this.customerRepository
                .findById(customerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No customer found"));

        Screening screening = this.screeningRepository
                .findById(screeningId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No screening found"));


        ticket.setCreatedAt(currentTime);
        ticket.setUpdatedAt(currentTime);
        ticket.setCustomer(customer);
        ticket.setScreening(screening);
        this.ticketRepository.save(ticket);
        return new ResponseEntity<>(new TicketResponse(ticket), HttpStatus.CREATED);
    }

}
