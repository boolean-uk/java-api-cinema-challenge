//package com.booleanuk.api.cinema.controllers;
//
//import com.booleanuk.api.cinema.models.Ticket;
//import com.booleanuk.api.cinema.repositories.CustomerRepository;
//import com.booleanuk.api.cinema.repositories.ScreeningRepository;
//import com.booleanuk.api.cinema.repositories.TicketRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("Tickets")
//public class TicketController {
//    @Autowired
//    private TicketRepository ticketRepository;
//    @Autowired
//    private CustomerRepository customerRepository;
//    @Autowired
//    ScreeningRepository screeningRepository;
//
//    @GetMapping
//    public List<Ticket> getAllTickets() {
//        return this.ticketRepository.findAll();
//    }
//
//    @PostMapping
//    public ResponseEntity<Ticket> addTicket(@RequestBody Ticket ticket) {
//
//        ticket.setUpdatedAt(null);
//        ticket.setCreatedAt(java.time.LocalDateTime.now());
//        return new ResponseEntity<>(this.ticketRepository.save(ticket), HttpStatus.CREATED);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Ticket> updateTicket(@RequestBody Ticket ticket,@PathVariable int id) {
//        Ticket updatedTicket = this.ticketRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Ticket Not Found"));
//
//    }
//
//}
