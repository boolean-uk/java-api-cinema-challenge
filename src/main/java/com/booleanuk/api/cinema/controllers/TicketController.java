package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Customer;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.models.Ticket;
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
@RequestMapping("customers/{customerID}/screenings/{screeningID}")
public class TicketController {
    @Autowired
    private TicketRepository repository;

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ScreeningRepository screeningRepository;

    @GetMapping
    public List<Ticket> getAll(@PathVariable final Integer customerID, @PathVariable final Integer screeningID) {
        final ArrayList<Ticket> _tickets = new ArrayList<>();

        for (Ticket ticket : getCustomer(customerID).getTickets())
            if (ticket.getScreening_id().getId().equals(screeningID))
                _tickets.add(ticket);

        return _tickets;
    }

    @PostMapping
    public ResponseEntity<Ticket> create(@PathVariable final Integer customerID, @PathVariable final Integer screeningID, @RequestBody Ticket request) {
        if (request.haveNullFields()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        final Customer _customer = getCustomer(customerID);
        final Screening _screening = getScreening(screeningID);

        _customer.getTickets().add(request);
        _screening.getTickets().add(request);

        request.setCustomer_id(_customer);
        request.setScreening_id(_screening);

        return new ResponseEntity<>(repository.save(request), HttpStatus.CREATED);
    }

    private Customer getCustomer(final Integer id) {
        return customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No customer with that ID was found."));
    }

    private Screening getScreening(final Integer id) {
        return screeningRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No screening with that ID was found."));
    }
}
