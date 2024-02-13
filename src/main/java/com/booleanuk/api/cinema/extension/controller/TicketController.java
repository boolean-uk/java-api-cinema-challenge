package com.booleanuk.api.cinema.extension.controller;

import com.booleanuk.api.cinema.extension.model.*;
import com.booleanuk.api.cinema.extension.repository.CustomerRepository;
import com.booleanuk.api.cinema.extension.repository.ScreeningRepository;
import com.booleanuk.api.cinema.extension.repository.TicketRepository;
import com.booleanuk.api.cinema.extension.response.CustomResponse;
import com.booleanuk.api.cinema.extension.response.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class TicketController {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ScreeningRepository screeningRepository;

    private final TicketDTOMapper ticketDTOMapper;

    public TicketController(TicketDTOMapper ticketDTOMapper) {
        this.ticketDTOMapper = ticketDTOMapper;
    }

    @GetMapping("/{customer_id}/screenings/{screening_id}")
    public ResponseEntity<CustomResponse> getTicketsByCustomerId(@PathVariable Long customer_id, @PathVariable Long screening_id) {
        if(!customerRepository.existsById(customer_id) || !screeningRepository.existsById(screening_id)) {
            return new ResponseEntity<>(new CustomResponse("error", new Error("not found")), HttpStatus.NOT_FOUND);
        }

        Customer customer = customerRepository
                .findById(customer_id).get();
        Screening screening = screeningRepository
                .findById(screening_id).get();

        List<Ticket> customResponses = new ArrayList<>(customer.getTickets());
        CustomResponse customResponse = new CustomResponse("success", customResponses.stream().map(ticketDTOMapper));

        return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }

    @PostMapping("/{customer_id}/screenings/{screening_id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CustomResponse> createTicket(@PathVariable Long customer_id, @PathVariable Long screening_id,  @RequestBody Ticket ticket) {
        if(!customerRepository.existsById(customer_id) || !screeningRepository.existsById(screening_id)) {
            return new ResponseEntity<>(new CustomResponse("error", new Error("not found")), HttpStatus.NOT_FOUND);
        }

        Customer customer = customerRepository
                .findById(customer_id).get();
        ticket.setCustomer(customer);

        Screening screening = screeningRepository
                .findById(screening_id).get();
        ticket.setScreening(screening);

        if (ticket.getCustomer() == null) {
            return new ResponseEntity<>(new CustomResponse("error", new Error("Ticket must have a customer")), HttpStatus.BAD_REQUEST);
        } else if (ticket.getScreening() == null) {
            return new ResponseEntity<>(new CustomResponse("error", new Error("Ticket must have a screening")), HttpStatus.BAD_REQUEST);
        } else if (ticket.getNumSeats() == null || ticket.getNumSeats() <= 0) {
            return new ResponseEntity<>(new CustomResponse("error", new Error("Ticket must have a number of seats")), HttpStatus.BAD_REQUEST);
        }

        ticketRepository.save(ticket);
        return new ResponseEntity<>(new CustomResponse("success", ticketRepository.findById(ticket.getId()).
                stream().map(ticketDTOMapper)), HttpStatus.CREATED);
    }
}
