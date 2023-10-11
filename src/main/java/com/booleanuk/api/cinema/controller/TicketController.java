package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import com.booleanuk.api.cinema.utility.DataResponse;
import com.booleanuk.api.cinema.utility.ErrorResponse;
import com.booleanuk.api.cinema.utility.Responses.TicketListResponse;
import com.booleanuk.api.cinema.utility.Responses.TicketResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
public class TicketController {
    @Autowired
    private TicketRepository tickets;
    @Autowired
    private CustomerRepository customers;
    @Autowired
    private ScreeningRepository screenings;

    @PostMapping("/customers/{customerId}/screenings/{screeningId}")
    public ResponseEntity<DataResponse<?>> createTicket(@PathVariable int customerId, @PathVariable int screeningId, @RequestBody Ticket ticket){
        Ticket ticketToCreate;
        try {
            ticket.setCreatedAt(ZonedDateTime.now());
            ticket.setUpdatedAt(ZonedDateTime.now());
            ticket.setCustomer(this.customers.findById(customerId).orElseThrow(NullPointerException::new));
            ticket.setScreening(this.screenings.findById(screeningId).orElseThrow(NullPointerException::new));
            ticketToCreate = this.tickets.save(ticket);
        } catch (NullPointerException e) {
            ErrorResponse error = new ErrorResponse();
            error.set("No customer or screening with those ids found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Could not create ticket, please check all fields are correct.");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        TicketResponse response = new TicketResponse();
        response.set(ticketToCreate);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/customers/{customerId}/screenings/{screeningId}")
    public ResponseEntity<DataResponse<?>> getTicket(@PathVariable int customerId, @PathVariable int screeningId){
        List<Ticket> ticketsToFind;
        try {
            Customer customer = this.customers.findById(customerId).orElseThrow(NullPointerException::new);
            Screening screening = this.screenings.findById(screeningId).orElseThrow(NullPointerException::new);
            ticketsToFind = this.tickets.findByCustomerAndScreening(customer, screening);
            if (ticketsToFind == null){
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            ErrorResponse error = new ErrorResponse();
            error.set("No customer or screening with those ids found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        TicketListResponse response = new TicketListResponse();
        response.set(ticketsToFind);
        return ResponseEntity.ok(response);
    }
}
