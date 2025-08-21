package com.booleanuk.api.cinema.ticket;

import com.booleanuk.api.cinema.customer.Customer;
import com.booleanuk.api.cinema.customer.CustomerRepository;
import com.booleanuk.api.cinema.movie.Movie;
import com.booleanuk.api.cinema.response.ErrorResponse;
import com.booleanuk.api.cinema.response.Response;
import com.booleanuk.api.cinema.response.TicketResponse;
import com.booleanuk.api.cinema.response.TicketResponseList;
import com.booleanuk.api.cinema.screening.Screening;
import com.booleanuk.api.cinema.screening.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ScreeningRepository screeningRepository;

    @PostMapping("customers/{customerId}/screenings/{screeningId}")
    public ResponseEntity<Response<?>> bookTicket(@PathVariable int customerId, @PathVariable int screeningId, @RequestBody Ticket ticket) {
        Customer customer = this.customerRepository.findById(customerId).orElse(null);
        Screening screening = this.screeningRepository.findById(screeningId).orElse(null);
        TicketResponse response = new TicketResponse();

        if (customer == null || screening == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Not found");
            return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
        }

        try {
            ticket.setCustomer(customer);
            ticket.setScreening(screening);
            ticket.setCreated_at(String.valueOf(LocalDateTime.now()));
            response.set(this.ticketRepository.save(ticket));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("customers/{customerId}/screenings/{screeningId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Response<?>> getAllTickets(@PathVariable int customerId, @PathVariable int screeningId) {
        Customer customer = this.customerRepository.findById(customerId).orElse(null);
        TicketResponseList response = new TicketResponseList();
        List<Ticket> output = new ArrayList<>();

        for(Ticket ticket : customer.getTickets()) {
            if(ticket.getScreening().getId() == screeningId) {
                output.add(ticket);
            }
        }

        response.set(output);

        if(output.isEmpty()) {
            ErrorResponse error = new ErrorResponse();
            error.set("Not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("tickets/{id}")
    public ResponseEntity<Response<?>> deleteTicket(@PathVariable int id) {
        Ticket deletedTicket = this.ticketRepository.findById(id).orElse(null);
        TicketResponse response = new TicketResponse();

        if (deletedTicket == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Not found");
            return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
        }
        this.ticketRepository.delete(deletedTicket);
        response.set(deletedTicket);
        return ResponseEntity.ok(response);
    }
}
