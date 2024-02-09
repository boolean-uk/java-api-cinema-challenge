package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import com.booleanuk.api.cinema.utility.DataResponse;
import com.booleanuk.api.cinema.utility.ErrorResponse;
import com.booleanuk.api.cinema.utility.responses.TicketListResponse;
import com.booleanuk.api.cinema.utility.responses.TicketRespons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
public class TicketController {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping("/customers/{customerId}/screenings/{screeningId}")
    public ResponseEntity<DataResponse<?>> getTicket(@PathVariable int customerId, @PathVariable int screeningId) {
        List<Ticket> findTicket;
        try {
            Customer customer = this.customerRepository.findById(customerId).orElseThrow(NullPointerException::new);
            Screening screening = this.screeningRepository.findById(screeningId).orElseThrow(NullPointerException::new);
            findTicket = this.ticketRepository.findByCustomerAndScreening(customer, screening);
            if (findTicket == null) {
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No customer or screening id found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        TicketListResponse ticketListResponse = new TicketListResponse();
        ticketListResponse.set(findTicket);
        return ResponseEntity.ok(ticketListResponse);
    }

    @PostMapping("/customers/{customerId}/screenings/{screeningId}")
    public ResponseEntity<DataResponse<?>> create(@PathVariable int customerId, @PathVariable int screeningId, @RequestBody Ticket ticket) {
        Ticket createTicket;
        try {
            ticket.setCreatedAt(ZonedDateTime.now());
            ticket.setUpdatedAt(ZonedDateTime.now());
            ticket.setCustomer(this.customerRepository.findById(customerId).orElseThrow(NullPointerException::new));
            ticket.setScreening(this.screeningRepository.findById(screeningId).orElseThrow(NullPointerException::new));
            createTicket = this.ticketRepository.save(ticket);

        } catch (NullPointerException e) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No customer or screening with this id");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Could not create ticket");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        TicketRespons ticketRespons = new TicketRespons();
        ticketRespons.set(createTicket);
        return new ResponseEntity<>(ticketRespons, HttpStatus.CREATED);
    }
}
