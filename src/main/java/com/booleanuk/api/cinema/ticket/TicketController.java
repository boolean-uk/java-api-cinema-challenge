package com.booleanuk.api.cinema.ticket;

import com.booleanuk.api.cinema.customer.Customer;
import com.booleanuk.api.cinema.customer.CustomerRepository;
import com.booleanuk.api.cinema.responses.ErrorResponse;
import com.booleanuk.api.cinema.responses.Response;
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
    public ResponseEntity<Response<?>> getAll(@PathVariable int cid, @PathVariable int sid){
        Screening tempScreening = screenings
                .findById(sid)
                .orElse(null);

        Customer tempCustomer = customers
                .findById(cid)
                .orElse(null);

        if (tempScreening == null || tempCustomer == null){
            ErrorResponse notFound = new ErrorResponse();
            notFound.set("not found");

            return new ResponseEntity<>(notFound, HttpStatus.NOT_FOUND);
        }

        List<Ticket> tickets = repo.findAllByScreeningAndCustomer(tempScreening, tempCustomer);
        Response<List<Ticket>> ticketListResponse = new Response<>();
        ticketListResponse.set(tickets);

        return ResponseEntity.ok(ticketListResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> create(@PathVariable int cid, @PathVariable int sid, @RequestBody Ticket ticket){
        if (ticket.getNumSeats() == 0){
            ErrorResponse badRequest = new ErrorResponse();
            badRequest.set("bad request");

            return new ResponseEntity<>(badRequest, HttpStatus.BAD_REQUEST);
        }

        Screening tempScreening = screenings
                .findById(sid)
                .orElse(null);
        Customer tempCustomer = customers
                .findById(cid)
                .orElse(null);

        if (tempScreening == null || tempCustomer == null){
            ErrorResponse notFound = new ErrorResponse();
            notFound.set("not found");

            return new ResponseEntity<>(notFound, HttpStatus.NOT_FOUND);
        }

        ticket.setScreening(tempScreening);
        ticket.setCustomer(tempCustomer);
        ticket.setCreatedAt(nowFormatted());
        ticket.setUpdatedAt(nowFormatted());
        repo.save(ticket);

        Response<Ticket> ticketResponse = new Response<>();
        ticketResponse.set(ticket);

        return new ResponseEntity<>(ticketResponse, HttpStatus.CREATED);
    }

    private String nowFormatted(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return now.format(format);
    }
}
