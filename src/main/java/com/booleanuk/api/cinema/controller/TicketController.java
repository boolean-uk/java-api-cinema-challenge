package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ScreeningRepository screeningRepository;


    @GetMapping("/tickets")
    public List<Ticket> getAllTickets() {
        return this.ticketRepository.findAll();
    }

//    @PostMapping("customers/{customerId}/screenings/{screeningId}")
//    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket, @PathVariable ("customerId") int customerId, @PathVariable("screeningId") int screeningId ) {
//
//        ticket.setCustomer(.findById(customer_id).orElseThrow(() ->
//                new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not create ticket as ticket as customer is not found!"));
//       screening.setTicket
//
//    }


}
