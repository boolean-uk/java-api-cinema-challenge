package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.exceptions.CustomDataNotFoundException;
import com.booleanuk.api.cinema.exceptions.CustomParameterConstraintException;
import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import com.booleanuk.api.cinema.util.DateCreater;
import com.booleanuk.api.cinema.util.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("customers/{customerId}/screenings/{screeningId}")
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping
    public ResponseEntity<SuccessResponse<?>> createTicket(@PathVariable (name = "customerId") int customerId, @PathVariable (name = "screeningId") int screeningId, @RequestBody Ticket ticket) {

        Customer customer = getACustomer(customerId);
        Screening screening = getAScreening(screeningId);



        Ticket newTicket = new Ticket(ticket.getNumSeats(), DateCreater.getCurrentDate(), DateCreater.getCurrentDate(), customer, screening);
        this.ticketRepository.save(newTicket);
        SuccessResponse<Ticket> successResponse = new SuccessResponse<>(newTicket);

        return new ResponseEntity<>(successResponse, HttpStatus.CREATED);    }


    @GetMapping
    public ResponseEntity<SuccessResponse<?>> getTickets(@PathVariable (name = "customerId") int customerId, @PathVariable (name = "screeningId") int screeningId) {

        Customer customer = getACustomer(customerId);
        Screening screening = getAScreening(screeningId);

        List<Ticket> ticketList = new ArrayList<>();

        for(Ticket ticket1: customer.getTickets()) {
            if(screening.getTickets().contains(ticket1)) {
                ticketList.add(ticket1);
            }
        }

        SuccessResponse<List<Ticket>> successResponse = new SuccessResponse<>(ticketList);


        return ResponseEntity.ok(successResponse);
    }

    private Screening getAScreening(int id) {
        return this.screeningRepository.findById(id).orElseThrow(() -> new CustomDataNotFoundException("No customer or screening with those ids found"));
    }

    private Customer getACustomer(int id) {
        return this.customerRepository.findById(id).orElseThrow(() -> new CustomParameterConstraintException("No customer or screening with those ids found"));
    }

}
