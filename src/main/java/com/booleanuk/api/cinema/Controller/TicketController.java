package com.booleanuk.api.cinema.Controller;

import com.booleanuk.api.cinema.Model.Customer;
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

import java.util.List;

@RestController
@RequestMapping("/customers/{customerId}/screenings/{screeningId}")
public class TicketController {

    @Autowired
    private TicketRepository repository;
    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private CustomerRepository customerRepository;


    @GetMapping
    public List<Ticket> getAll() {
        return this.repository.findAll();
    }

    @PostMapping
    public ResponseEntity<Ticket> create(
            @PathVariable ( name= "customerId") Integer customerId,
            @PathVariable ( name= "screeningId") Integer screeningId,
            @RequestBody Ticket request) {
        validate(request);

        Customer customer = this.customerRepository.findById(customerId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));

        Screening screening = this.screeningRepository.findById(screeningId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));

        Ticket ticket = new Ticket(customer, screening, request.getNumSeats());

        return new ResponseEntity<Ticket>(this.repository.save(ticket), HttpStatus.CREATED);
    }

    public void validate(Ticket ticket) {
        if (ticket.getNumSeats() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Could not create/update Ticket, please check all required fields are correct.");
        }
    }


}
