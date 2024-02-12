package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import com.booleanuk.api.cinema.response.BadRequestResponse;
import com.booleanuk.api.cinema.response.NotFoundResponse;
import com.booleanuk.api.cinema.response.Response;
import com.booleanuk.api.cinema.response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{customerId}/screenings/{screeningId}")
    public ResponseEntity<Response> getAll(@PathVariable int customerId, @PathVariable int screeningId) {
        Screening tempScreening = screeningRepository.findById(screeningId).orElse(null);
        Customer tempCustomer = customerRepository.findById(customerId).orElse(null);

        if(tempScreening == null || tempCustomer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new NotFoundResponse());
        }

        //Find all tickets that have the given customer and screening id
        List<Ticket> tickets = ticketRepository.findAll().stream()
                .filter(ticket ->
                        ticket.getCustomer().getId() == customerId && ticket.getScreening().getId() == screeningId)
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse(tickets));
    }

    @PostMapping("/{customerId}/screenings/{screeningId}")
    public ResponseEntity<Response> createTicket(@PathVariable int customerId, @PathVariable int screeningId, @RequestBody Ticket ticket) {
        Screening tempScreening = screeningRepository.findById(screeningId).orElse(null);
        Customer tempCustomer = customerRepository.findById(customerId).orElse(null);

        if(tempScreening == null || tempCustomer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new NotFoundResponse());
        }

        ticket.setCustomer(tempCustomer);
        ticket.setScreening(tempScreening);

        if(containsNull(ticket)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BadRequestResponse());
        }
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse(ticketRepository.save(ticket)));
    }

//
//    private Ticket findTicket(int id) {
//        return ticketRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No tickets with that id were found"));
//    }

    private boolean containsNull(Ticket ticket) {
        return ticket.getNumSeats() == null;
    }
}
