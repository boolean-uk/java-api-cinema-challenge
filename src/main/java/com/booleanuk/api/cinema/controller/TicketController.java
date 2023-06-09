package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.ApiResponse;
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
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.parser.Entity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("customers/")
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ScreeningRepository screeningRepository;

    @GetMapping("{customerId}/screenings/{screeningId}")
    public ResponseEntity<ApiResponse<List<Ticket>>> getAllTicketsOfScreening(@PathVariable(name = "customerId") int customerId, @PathVariable(name = "screeningId") int screeningId){
        Customer customer = this.customerRepository.findById(customerId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Customer not found"));
        Screening screening = this.screeningRepository.findById(screeningId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Screening not found"));
        List<Ticket> tickets = this.ticketRepository.findTicketsByCustomerIdAndScreeningId(customer.getId(),screening.getId());

        if(tickets.size()>0)
            return ResponseEntity.ok(new ApiResponse<>("success",tickets));
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>("error",tickets));

    }

    @PostMapping("{customerId}/screenings/{screeningId}")
    public ResponseEntity<ApiResponse<Ticket>> createTicket(@PathVariable(name = "customerId") int customerId, @PathVariable(name = "screeningId") int screeningId, @RequestBody Ticket ticket) {

        Customer customer = this.customerRepository.findById(customerId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Customer not found"));
        Screening screening = this.screeningRepository.findById(screeningId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Screening not found"));
        List<Ticket> sameScreeningTickets = this.ticketRepository.findTicketsByScreeningId(screeningId);

        int remainingCapacity=0;
        for(Ticket similarTicket:sameScreeningTickets){
            remainingCapacity += similarTicket.getNumSeats();
        }

        if((screening.getCapacity()-remainingCapacity-ticket.getNumSeats())<0){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Screening Not enough capacity!");
        }
        Ticket ticketToCreate = new Ticket();
        ticketToCreate.setCustomer(customer);
        ticketToCreate.setScreening(screening);
        ticketToCreate.setNumSeats(ticket.getNumSeats());
        Ticket savedTicket = this.ticketRepository.save(ticketToCreate);
        return new ResponseEntity<>(new ApiResponse<>("success",savedTicket), HttpStatus.CREATED);

    }
}
