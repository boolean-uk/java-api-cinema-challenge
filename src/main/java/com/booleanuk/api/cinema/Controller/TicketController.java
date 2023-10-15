package com.booleanuk.api.cinema.Controller;

import com.booleanuk.api.cinema.ApiResponse.ApiResponse;
import com.booleanuk.api.cinema.Model.Ticket;
import com.booleanuk.api.cinema.Repository.CustomerRepository;
import com.booleanuk.api.cinema.Repository.ScreeningRepository;
import com.booleanuk.api.cinema.Repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

@RestController
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/customer/{customerId}/screenings/{screeningId}")
    public ApiResponse<List<Ticket>> getAllScreenings(@PathVariable (name = "customerId") int customerId, @PathVariable (name = "screeningId") int screeningId) {
        List<Ticket> tickets = this.ticketRepository.findByCustomerIdAndScreeningId(customerId, screeningId);
        return new ApiResponse<>("success", tickets);
    }

    @PostMapping("customer/{customerId}/screenings/{screeningId}")
    public ResponseEntity<ApiResponse<Ticket>> createScreening(@PathVariable (name = "customerId") int customerId, @PathVariable (name = "screeningId") int screeningId,@RequestBody Ticket ticket) {
        ticket.setCustomer(this.customerRepository.findById(customerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No customer matching this id")));
        ticket.setScreening(this.screeningRepository.findById(screeningId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No screening matching this id")));
        return new ResponseEntity<>(new ApiResponse<>("success", this.ticketRepository.save(ticket)), HttpStatus.CREATED);
    }
}
