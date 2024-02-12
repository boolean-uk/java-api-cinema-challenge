package com.booleanuk.api.controller;

import com.booleanuk.api.model.Customer;
import com.booleanuk.api.model.Screening;
import com.booleanuk.api.model.Ticket;
import com.booleanuk.api.repository.CustomerRepository;
import com.booleanuk.api.repository.ScreeningRepository;
import com.booleanuk.api.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("customers")
public class TicketController {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ScreeningRepository screeningRepository;

    @GetMapping("/{customer_id}/screenings/{screening_id}")
    public List<Ticket> getAllTickets(@PathVariable int customer_id,
                                      @PathVariable int screening_id) {
        checkIfCustomerExists(customer_id);
        checkIfScreeningExists(screening_id);

        List<Ticket> allSpecifiedTickets = new ArrayList<>();

        for (Ticket ticket : this.ticketRepository.findAll()) {
            if (ticket.getCustomer().getId() == customer_id &&
                    ticket.getScreening().getId() == screening_id) {
                allSpecifiedTickets.add(ticket);
            }
        }
        return allSpecifiedTickets;
    }

    @PostMapping("/{customer_id}/screenings/{screening_id}")
    public ResponseEntity<Ticket> createTicket(@PathVariable int customer_id,
                                               @PathVariable int screening_id,
                                               @RequestBody Ticket ticket) {
        checkAllTicketFields(ticket);
        Customer tempCustomer = getCustomerOrNotFound(customer_id);
        Screening tempScreening = getScreeningOrNotFound(screening_id);
        ticket.setCustomer(tempCustomer);
        ticket.setScreening(tempScreening);
        return ResponseEntity.ok(this.ticketRepository.save(ticket));
    }

    @DeleteMapping("/{customer_id}/screenings/{screening_id}")
    public ResponseEntity<Ticket> deleteTicket(@PathVariable int customer_id,
                                               @PathVariable int screening_id,
                                               @PathVariable int id) {
        checkIfCustomerExists(customer_id);
        checkIfScreeningExists(screening_id);
        Ticket ticketToDelete = getTicketOrNotFound(id);
        this.ticketRepository.delete(ticketToDelete);
        return ResponseEntity.ok(ticketToDelete);
    }

    //--------------------------- Private section---------------------------//

    private Ticket getTicketOrNotFound(int id) {
        return this.ticketRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No ticket with that ID"));
    }

    private Customer getCustomerOrNotFound(int id) {
        return this.customerRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No customer with that ID"));
    }

    private Screening getScreeningOrNotFound(int id) {
        return this.screeningRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No screening with that ID"));
    }

    private void checkIfCustomerExists(int id) {
        try {
            this.customerRepository.findById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No customer with that ID");
        }
    }

    private void checkIfScreeningExists(int id) {
        try {
            this.screeningRepository.findById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No screening with that ID");
        }
    }

    private void checkAllTicketFields(Ticket ticket) {
        if (ticket.getNumSeats() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Bad data in RequestBody");
        }
    }
}
