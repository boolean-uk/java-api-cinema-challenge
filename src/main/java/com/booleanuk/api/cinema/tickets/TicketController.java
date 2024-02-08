package com.booleanuk.api.cinema.tickets;

import com.booleanuk.api.cinema.customers.Customer;
import com.booleanuk.api.cinema.customers.CustomerRepository;
import com.booleanuk.api.cinema.screenings.Screening;
import com.booleanuk.api.cinema.screenings.ScreeningRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @GetMapping
    public List<Ticket> getAll(@PathVariable int customerId, @PathVariable int screeningId){
        return this.ticketRepository.findAllByCustomerIdAndScreeningId(customerId, screeningId);
    }
    @PostMapping
    public ResponseEntity<Ticket> createOne(@PathVariable int customerId, @PathVariable int screeningId, @Valid @RequestBody Ticket ticket){
        Customer customer = this.customerRepository
                .findById(customerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No customer found"));

        Screening screening = this.screeningRepository.findById(screeningId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No screening found"));

        if(screening.getCapacity() < ticket.getNumSeats()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough seats");
        }
        screening.setCapacity(screening.getCapacity() - ticket.getNumSeats());
        ticket.setCustomer(customer);
        ticket.setScreening(screening);
        this.screeningRepository.save(screening);


        return new ResponseEntity<>(this.ticketRepository.save(ticket), HttpStatus.CREATED);
    }
}
