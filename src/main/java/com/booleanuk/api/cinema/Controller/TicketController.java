package com.booleanuk.api.cinema.Controller;

import com.booleanuk.api.cinema.Model.Customer;
import com.booleanuk.api.cinema.Model.Movie;
import com.booleanuk.api.cinema.Model.Screening;
import com.booleanuk.api.cinema.Model.Ticket;
import com.booleanuk.api.cinema.Repository.CustomerRepository;
import com.booleanuk.api.cinema.Repository.MovieRepository;
import com.booleanuk.api.cinema.Repository.ScreeningRepository;
import com.booleanuk.api.cinema.Repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("customers/{customerId}/screenings/{screeningId}")
public class TicketController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @PostMapping()
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket, @PathVariable("screeningId") Integer
            screeningId, @PathVariable("customerId") Integer customerId){


        try {


            Screening screening = this.screeningRepository.findById(
                    screeningId).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No screening with that id exists")
            );

            Customer customer = this.customerRepository.findById(
                    customerId).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No customer with that id exists")
            );




            ticket.setCustomer(customer);
            ticket.setScreening(screening);
            return new ResponseEntity<Ticket>(this.ticketRepository.save(ticket),
                    HttpStatus.CREATED);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create ticket for the specified " +
                    "customer/screening, please check all required fields are correct.");
        }


    }

    @GetMapping()
    public ResponseEntity<List<Ticket>> getAll(@PathVariable("screeningId") Integer
            screeningId, @PathVariable("customerId") Integer customerId) {
        List<Ticket> allTickets=new ArrayList<>();


        Customer customer = this.customerRepository.findById(
                customerId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No customer with that id exists")
        );

        Screening screening = this.screeningRepository.findById(
                screeningId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No customer with that id exists")
        );

        return new ResponseEntity<List<Ticket>>(screening.getTickets(), HttpStatus.FOUND);

    }
}
