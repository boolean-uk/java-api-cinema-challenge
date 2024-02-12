package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
public class TicketController {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ScreeningRepository screeningRepository;

    @GetMapping("customers/{customerID}/screenings/{screeningID}")
    public ResponseEntity<?> getAllTicket(@PathVariable int customerID, @PathVariable int screeningID){
        Customer customer = this.customerRepository.findById(customerID).orElse(null);
        if (customer == null) {
            return new ResponseEntity<>("Customer could not be found", HttpStatus.NOT_FOUND);
        }

        Screening screening = this.screeningRepository.findById(screeningID).orElse(null);
        if (screening == null) {
            return new ResponseEntity<>("Screening could not be found", HttpStatus.NOT_FOUND);
        }
        List<Ticket> tickets = this.ticketRepository.findByCustomerIdAndScreeningId(customerID, screeningID);
        if (tickets.isEmpty()) {
            return new ResponseEntity<>("No tickets found for this customer and screening", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(tickets);
    }

    @PostMapping("customers/{customerID}/screenings/{screeningID}")
    public ResponseEntity<?> addTicket(@PathVariable int customerID, @PathVariable int screeningID, @RequestBody Ticket ticket){
        Customer customer = this.customerRepository.findById(customerID).orElse(null);
        if (customer == null) {
            return new ResponseEntity<>("Customer could not be found", HttpStatus.NOT_FOUND);
        }
        Screening screening = this.screeningRepository.findById(screeningID).orElse(null);
        if (screening == null) {
            return new ResponseEntity<>("Screening could not be found", HttpStatus.NOT_FOUND);
        }
        Ticket ticket1 = new Ticket();
        ticket1.setCustomer(customer);
        ticket1.setScreening(screening);
        ticket1.setNumSeats(ticket.getNumSeats());
        ticket1.setUpdatedAt(ZonedDateTime.now());


        return new ResponseEntity<>(this.ticketRepository.save(ticket1), HttpStatus.CREATED);
    }
}
