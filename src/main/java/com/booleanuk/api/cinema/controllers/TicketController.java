package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Customer;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.models.Ticket;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("customers")
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("{customerId}/screenings/{screeningId}")
        public ResponseEntity<Ticket> createTicket(@PathVariable(name = "customerId") int customerId, @PathVariable(name = "screeningId") int screeningId, @RequestBody Ticket ticket){

            Customer customer = this.customerRepository.findById(customerId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found 1.."));
            Screening screening = this.screeningRepository.findById(screeningId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found. 2."));

            ticket.setCustomer(customer);
            ticket.setScreening(screening);
            ticket.setCreatedAt(LocalDateTime.now());
            ticket.setUpdatedAt(LocalDateTime.now());

            return new ResponseEntity<Ticket>(this.ticketRepository.save(ticket), HttpStatus.CREATED);
    }

    @GetMapping("{customerId}/screenings/{screeningId}")
    public ResponseEntity<List<Ticket>> getAllTickets (@PathVariable(name = "customerId") int customerId, @PathVariable(name = "screeningId") int screeningId){

        Customer customer = this.customerRepository.findById(customerId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found ..1"));
        Screening screening = this.screeningRepository.findById(screeningId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found ..2"));

        List<Ticket> ticketList = this.ticketRepository.findAllByCustomerIdAndScreeningId(customer.getId(), screening.getId());

        return ResponseEntity.ok(ticketList);
    }

}
