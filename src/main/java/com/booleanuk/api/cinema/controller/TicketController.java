package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import com.booleanuk.api.cinema.response.ErrorResponse;
import com.booleanuk.api.cinema.response.Response;
import com.booleanuk.api.cinema.response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

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
    public ResponseEntity<Response> getAllTickets(@PathVariable int customerId, @PathVariable int screeningId) {
        // 404 Not found if no customer with given ID
        if (this.getCustomer(customerId) == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No customer with that ID found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        // 404 Not found if no screening with given ID
        if (this.getScreening(screeningId) == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No screening with that ID found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        // Makes list of all tickets with given customerId and screeningId
        List<Ticket> tickets = this.ticketRepository.findAll().stream().filter(ticket ->
                ticket.getCustomer().getId() == customerId && ticket.getScreening().getId() == screeningId).toList();

        // Response with the list of tickets
        SuccessResponse response = new SuccessResponse();
        response.set(tickets);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response> bookTicket(
            @PathVariable int customerId, @PathVariable int screeningId, @RequestBody Ticket ticket) {
        // 404 Not found if no customer with given ID
        Customer customer = this.getCustomer(customerId);
        if (customer == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No customer with that ID found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        ticket.setCustomer(customer);

        // 404 Not found if no screening with given ID
        Screening screening = this.getScreening(screeningId);
        if (screening == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No screening with that ID found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        ticket.setScreening(screening);

        // 400 Bad request if capacity is smaller than requested amount of seats
        Response capacityResponse = this.checkCapacityOfScreening(screening, ticket.getNumSeats());
        if (capacityResponse.getClass().equals(ErrorResponse.class)) {
            return new ResponseEntity<>(capacityResponse, HttpStatus.BAD_REQUEST);
        }

        ticket.setCreatedAt(ZonedDateTime.now());
        ticket.setUpdatedAt(ZonedDateTime.now());

        // Response with the booked ticket
        SuccessResponse response = new SuccessResponse();
        response.set(this.ticketRepository.save(ticket));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Finds customer by ID
    private Customer getCustomer(int customerId) {
        return this.customerRepository.findById(customerId).orElse(null);
    }

    // Finds screening by ID
    private Screening getScreening(int screeningId) {
        return this.screeningRepository.findById(screeningId).orElse(null);
    }

    // Check capacity of screening, returns ErrorResponse if numSeats are too large
    private Response checkCapacityOfScreening(Screening screening, int numSeats) {
        int maxCapacity = screening.getCapacity();
        int ticketsSold = this.ticketRepository.findAll().stream()
                .filter(ticket -> ticket.getScreening() == screening)
                .mapToInt(Ticket::getNumSeats).sum();
        // Bad request error if number of available seats is smaller than the requested amount
        if (numSeats > (maxCapacity - ticketsSold)) {
            ErrorResponse error = new ErrorResponse();
            error.set("Number of seats are too large.");
            return error;
        }
        return new SuccessResponse();
    }
}
