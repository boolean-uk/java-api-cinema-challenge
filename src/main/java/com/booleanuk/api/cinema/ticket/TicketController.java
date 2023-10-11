package com.booleanuk.api.cinema.ticket;

import com.booleanuk.api.cinema.customer.Customer;
import com.booleanuk.api.cinema.customer.CustomerRepository;
import com.booleanuk.api.cinema.response.ErrorResponse;
import com.booleanuk.api.cinema.response.Response;
import com.booleanuk.api.cinema.response.TicketListResponse;
import com.booleanuk.api.cinema.response.TicketResponse;
import com.booleanuk.api.cinema.screening.Screening;
import com.booleanuk.api.cinema.screening.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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
    public ResponseEntity<Response<?>> getAllTickets(@PathVariable int customerId, @PathVariable int screeningId) {
        TicketListResponse ticketListResponse = new TicketListResponse();

        Customer customer = this.customerRepository.findById(customerId).orElse(null);
        Screening screening = this.screeningRepository.findById(screeningId).orElse(null);

        if (customer == null || screening == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Customer or screening not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        ticketListResponse.set(this.ticketRepository.findByCustomerIdAndScreeningId(customerId, screeningId));
        return ResponseEntity.ok(ticketListResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> bookTicket(@PathVariable int customerId, @PathVariable int screeningId, @RequestBody Ticket ticket) {
        TicketResponse ticketResponse = new TicketResponse();

        if (ticket.getNumSeats() == 0) {
            ErrorResponse error = new ErrorResponse();
            error.set("The number of seats is 0");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

        } else {
            Date today = new Date();
            ticket.setCreatedAt(today);
            ticket.setUpdatedAt(today);

            ticketResponse.set(this.ticketRepository.save(ticket));
        }
        return new ResponseEntity<>(ticketResponse, HttpStatus.CREATED);
    }
}
