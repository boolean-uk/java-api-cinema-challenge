package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.dtos.ResponseDTO;
import com.booleanuk.api.cinema.exceptions.BadRequestException;
import com.booleanuk.api.cinema.exceptions.NotFoundException;
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

import java.util.List;

@RestController
@RequestMapping("customers/{customerId}/screenings/{screeningId}")
public class TicketController {

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ScreeningRepository screeningRepository;

    @GetMapping
    public ResponseEntity<?> getAll(@PathVariable int customerId, @PathVariable int screeningId) {
        Customer customer = this.customerRepository.findById(customerId).orElseThrow(
                () -> new NotFoundException("No customer with that id: " + screeningId + " found")
        );

        Screening screening = this.screeningRepository.findById(screeningId).orElseThrow(
                () -> new NotFoundException("No screening with that id: " + screeningId + " found")
        );
        List<Ticket> tickets = ticketRepository.findByCustomerAndScreening(customer, screening);

        ResponseDTO<List<Ticket>> response = new ResponseDTO<>(
                "success",
                tickets);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody Ticket ticket, @PathVariable int customerId, @PathVariable int screeningId) {

        Customer customer = this.customerRepository.findById(customerId).orElseThrow(
                () -> new NotFoundException("No customer with that id: " + screeningId + " found")
        );

        Screening screening = this.screeningRepository.findById(screeningId).orElseThrow(
                () -> new NotFoundException("No screening with that id: " + screeningId + " found")
        );
        try{
            ticket.setCustomer(customer);
            ticket.setScreening(screening);
            this.ticketRepository.save(ticket);
            ResponseDTO<Screening> response = new ResponseDTO<>(
                    "success",
                    screening);

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            throw new BadRequestException("bad request");
        }

    }

}
