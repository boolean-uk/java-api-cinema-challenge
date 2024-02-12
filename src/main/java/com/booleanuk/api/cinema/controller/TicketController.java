package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import com.booleanuk.api.cinema.response.CustomResponse;
import com.booleanuk.api.cinema.response.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("customers")
public class TicketController{

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private ScreeningRepository screeningRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/{customerId}/screenings/{screeningId}")
    public ResponseEntity<CustomResponse> getAllTickets(@PathVariable int customerId, @PathVariable int screeningId) {
        if (ticketRepository.findByCustomerIdAndScreeningId(customerId, screeningId).isEmpty()) {
            CustomResponse errResponse = new CustomResponse("Error", new Error("No data found"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errResponse);
        }

        List<Ticket> tickets = this.ticketRepository.findByCustomerIdAndScreeningId(customerId, screeningId);

            return ResponseEntity.ok(new CustomResponse("Success", tickets));

    }


    @PostMapping("/{customerId}/screenings/{screeningId}")
    public ResponseEntity<CustomResponse> createTicket(@PathVariable int customerId, @PathVariable int screeningId, @RequestBody Ticket ticket) {
        if (ticket.getCustomer() == null || ticket.getScreening() == null || ticket.getNumSeats() == 0) {
            CustomResponse errResponse = new CustomResponse("Error", new Error("Check if all fields are correct!"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errResponse);
        }

        Customer tempCus = this.customerRepository.findById(customerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No customer found"));
        ticket.setCustomer(tempCus);
        Screening tempScr = this.screeningRepository.findById(screeningId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No screening found"));
        ticket.setScreening(tempScr);

        return ResponseEntity.ok(new CustomResponse("success", this.ticketRepository.save(ticket)));
    }

}
