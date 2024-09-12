package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.ApiResponse;
import com.booleanuk.api.cinema.dto.TicketDTO;
import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers/{customerId}/screenings/{screeningId}")
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ScreeningRepository screeningRepository;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Ticket> create(
            @PathVariable int customerId,
            @PathVariable int screeningId,
            @RequestBody TicketDTO body
    ) {

        Customer customer = this.customerRepository.findById(customerId).orElse(null);
        Screening screening = this.screeningRepository.findById(screeningId).orElse(null);

        Ticket ticket = new Ticket();
        ticket.setNumSeats(body.getNumSeats());
        ticket.setCustomer(customer);
        ticket.setScreening(screening);

        Ticket savedTicket = this.ticketRepository.save(ticket);
        return new ApiResponse<>("success", savedTicket);
    }
}
