package com.booleanuk.api.cinema.controller;


import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import com.booleanuk.api.cinema.response.ErrorResponse;
import com.booleanuk.api.cinema.response.TicketListResponse;
import com.booleanuk.api.cinema.response.TicketResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping("customers/{customer_id}/screenings/{screening_id}")
public class TicketController {

    @Autowired
    private TicketRepository repository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ScreeningRepository screeningRepository;

    public TicketController(TicketRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<Object> getAll(@PathVariable int customer_id, @PathVariable int screening_id) {
        TicketListResponse ticketListResponse = new TicketListResponse();
        List<Ticket> tickets = this.repository.findAllByCustomer_idAndScreening_id(customer_id, screening_id);
        if (tickets.isEmpty()) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        ticketListResponse.set(tickets);
        return ResponseEntity.ok(ticketListResponse);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Object> createTicket(@PathVariable int customer_id, @PathVariable int screening_id, @RequestBody Ticket ticket) {

        Customer customer = this.customerRepository.findById(customer_id).orElse(null);
        if (customer == null){
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        Screening screening = this.screeningRepository.findById(screening_id).orElse(null);
        if (screening == null){
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        ticket.setCustomer(customer);
        ticket.setScreening(screening);

        TicketResponse ticketResponse = new TicketResponse();
        ticketResponse.set(this.repository.save(ticket));
        return ResponseEntity.ok(ticketResponse);
    }

}

