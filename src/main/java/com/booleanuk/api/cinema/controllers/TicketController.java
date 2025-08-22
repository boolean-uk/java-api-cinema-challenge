package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Customer;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.models.Ticket;
import com.booleanuk.api.cinema.payload.response.*;
import com.booleanuk.api.cinema.repositories.CustomerRepository;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import com.booleanuk.api.cinema.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
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
    public ResponseEntity<TicketListResponse> getAllTickets(@PathVariable int customerId, @PathVariable int screeningId){
        Customer customer = this.customerRepository.findById(customerId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No customer with that id were found"));

        Screening screening = this.screeningRepository.findById(screeningId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No screening with that id were found"));

        List<Ticket> customerTickets = new ArrayList<>();

        for (Ticket ticket: this.ticketRepository.findAll()){
            if(ticket.getCustomer() == customer && ticket.getScreening() == screening){
                customerTickets.add(ticket);
            }
        }
        TicketListResponse ticketListResponse = new TicketListResponse();
        ticketListResponse.set(customerTickets);
        return ResponseEntity.ok(ticketListResponse);
    }


    @PostMapping
    public ResponseEntity<Response<?>> createTicket(@PathVariable int customerId, @PathVariable int screeningId, @RequestBody Ticket ticket) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No customer with that id were found"));

        Screening screening = screeningRepository.findById(screeningId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No screening with that id were found"));

        ticket.setCustomer(customer);
        ticket.setScreening(screening);

        TicketResponse ticketResponse = new TicketResponse();
        try{
            ticketResponse.set(this.ticketRepository.save(ticket));
            customer.addTicket(ticket);
            screening.addTicket(ticket);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ticketResponse, HttpStatus.CREATED);
    }
}
