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
import java.util.List;

@RestController
@RequestMapping("tickets")
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ScreeningRepository screeningRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getById(@PathVariable int id){
        Ticket employee = this.ticketRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        return ResponseEntity.ok(employee);

    }
    @GetMapping
    public List<Ticket> getAll(){
        return this.ticketRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Ticket> create(@RequestBody Ticket ticket){
        //if you have many to one relation in employee 'class for department, then
        //you need to do it like this, by making a temp department.
        Customer tempAuthor = customerRepository.findById(ticket.getCustomer()
                        .getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No author with ID"));
        ticket.setCustomer(tempAuthor);

        Screening tempScreening = screeningRepository.findById(ticket.getScreening()
                        .getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No publisher with ID"));
        ticket.setScreening(tempScreening);

        return ResponseEntity.ok(ticketRepository.save(ticket));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Ticket> delete(@PathVariable int id){
        Ticket delete = this.ticketRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        this.ticketRepository.delete(delete);
        return ResponseEntity.ok(delete);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ticket> updateEmployee(@PathVariable int id, @RequestBody Ticket book){
        Ticket update = this.ticketRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));

        Customer tempCustomer = customerRepository.findById(book.getCustomer()
                        .getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No author with ID"));


        Screening tempScreening = screeningRepository.findById(book.getScreening()
                        .getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No publisher with ID"));

        update.setCustomer(tempCustomer);
        update.setScreening(tempScreening);
        update.setCreatedAt(book.getCreatedAt());
        update.setUpdatedAt(book.getUpdatedAt());
        return new ResponseEntity<>(this.ticketRepository.save(update), HttpStatus.CREATED);
    }
}
