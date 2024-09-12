package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.ApiResponse;
import com.booleanuk.api.cinema.dto.TicketDTO;
import com.booleanuk.api.cinema.exception.NotFoundException;
import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers/{customerId}/screenings/{screeningId}")
public class TicketController {
    private final TicketRepository ticketRepository;
    private final CustomerRepository customerRepository;
    private final ScreeningRepository screeningRepository;

    @Autowired
    public TicketController(TicketRepository ticket, CustomerRepository customer, ScreeningRepository screening) {
        this.ticketRepository = ticket;
        this.customerRepository = customer;
        this.screeningRepository = screening;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Ticket> create(
            @PathVariable int customerId,
            @PathVariable int screeningId,
            @RequestBody TicketDTO body
    ) {

        Customer customer = this.customerRepository.findById(customerId).orElse(null);
        Screening screening = this.screeningRepository.findById(screeningId).orElse(null);

        if (customer == null || screening == null) {
            throw new NotFoundException("not found");
        }

        Ticket ticket = new Ticket();
        ticket.setNumSeats(body.getNumSeats());
        ticket.setCustomer(customer);
        ticket.setScreening(screening);

        Ticket savedTicket = this.ticketRepository.save(ticket);
        return new ApiResponse<>("success", savedTicket);
    }

    @GetMapping
    public ApiResponse<List<Ticket>> getAll(@PathVariable int customerId, @PathVariable int screeningId) {
        Customer customer = this.customerRepository.findById(customerId).orElse(null);
        Screening screening = this.screeningRepository.findById(screeningId).orElse(null);

        if (customer == null || screening == null) {
            throw new NotFoundException("not found");
        }
        return new ApiResponse<>("success", this.ticketRepository.findAll());
    }
}
