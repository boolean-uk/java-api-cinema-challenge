package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Customer;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.models.Ticket;
import com.booleanuk.api.cinema.models.TicketRequestDTO;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import com.booleanuk.api.cinema.payload.response.TicketListResponse;
import com.booleanuk.api.cinema.payload.response.TicketResponse;
import com.booleanuk.api.cinema.payload.response.ErrorResponse;
import com.booleanuk.api.cinema.payload.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<TicketListResponse> getAllTickets() {
        TicketListResponse ticketListResponse = new TicketListResponse();
        ticketListResponse.set(this.ticketRepository.findAll());
        return ResponseEntity.ok(ticketListResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createTicket(@RequestBody TicketRequestDTO dto, @PathVariable int customerId, @PathVariable int screeningId) {
        TicketResponse ticketResponse = new TicketResponse();
        try {
            Customer customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new RuntimeException("Customer not found"));
            Screening screening = screeningRepository.findById(screeningId)
                    .orElseThrow(() -> new RuntimeException("Screening not found"));

            Ticket ticket = new Ticket();
            ticket.setNumSeats(dto.numSeats());
            ticket.setCustomer(customer);
            ticket.setScreening(screening);

            ticketResponse.set(ticketRepository.save(ticket));
            return new ResponseEntity<>(ticketResponse, HttpStatus.CREATED);

        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request: " + e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }
}
