package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.Customer;
import com.booleanuk.api.cinema.DTO.NotFoundException;
import com.booleanuk.api.cinema.DTO.TicketDTO;
import com.booleanuk.api.cinema.Screening;
import com.booleanuk.api.cinema.Ticket;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers/{customerId}/screenings/{screeningId}")
public class TicketController {

    private final TicketRepository ticketRepository;
    private final CustomerRepository customerRepository;
    private final ScreeningRepository screeningRepository;

    @Autowired
    public TicketController(ScreeningRepository screeningRepository, TicketRepository ticketRepository, CustomerRepository customerRepository){
        this.ticketRepository = ticketRepository;
        this.customerRepository = customerRepository;
        this.screeningRepository = screeningRepository;
    }

    @GetMapping
    public List<Ticket> getAllTickets(){
        return this.ticketRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Ticket> createTicket(@PathVariable int customerId, @PathVariable int screeningId, @RequestBody TicketDTO ticketDTO) {
        Customer customer = this.customerRepository.findById(customerId).orElseThrow(
                () -> new NotFoundException("No customer with that ID found")
        );
        Screening screening = this.screeningRepository.findById(screeningId).orElseThrow(
                () -> new NotFoundException("No screening with that ID found")
        );

        Ticket newTicket = new Ticket();
        newTicket.setNumSeats(ticketDTO.getNumSeats());
        newTicket.setScreening(screening);
        newTicket.setCustomer(customer);
        return new ResponseEntity<>(this.ticketRepository.save(newTicket), HttpStatus.CREATED);
    }
}




