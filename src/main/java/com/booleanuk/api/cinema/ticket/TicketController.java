package com.booleanuk.api.cinema.ticket;

import com.booleanuk.api.cinema.customer.Customer;
import com.booleanuk.api.cinema.customer.CustomerRepository;
import com.booleanuk.api.cinema.screening.Screening;
import com.booleanuk.api.cinema.screening.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("customers/{cid}/screenings/{sid}")
public class TicketController {
    @Autowired
    private TicketRepository repo;

    @Autowired
    private ScreeningRepository screenings;

    @Autowired
    private CustomerRepository customers;

    @GetMapping
    public ResponseEntity<List<Ticket>> getAll(@PathVariable int cid, @PathVariable int sid){
        Screening tempScreening = screenings
                .findById(sid)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "No screening with given ID."));

        Customer tempCustomer = customers
                .findById(cid)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "No customer with given ID."));

        List<Ticket> tickets = repo.findAllByScreeningAndCustomer(tempScreening, tempCustomer);

        return ResponseEntity.ok(tickets);
    }

    @PostMapping
    public ResponseEntity<Ticket> create(@PathVariable int cid, @PathVariable int sid, @RequestBody Ticket ticket){
        Screening tempScreening = screenings
                .findById(sid)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "No screening with given ID."));
        Customer tempCustomer = customers
                .findById(cid)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "No customer with given ID."));

        ticket.setScreening(tempScreening);
        ticket.setCustomer(tempCustomer);
        ticket.setCreatedAt(nowFormatted());
        ticket.setUpdatedAt(nowFormatted());

        return new ResponseEntity<Ticket>(repo.save(ticket), HttpStatus.CREATED);
    }

    private String nowFormatted(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return now.format(format);
    }
}
