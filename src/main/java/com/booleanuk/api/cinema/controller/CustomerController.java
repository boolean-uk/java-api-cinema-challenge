package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ScreeningRepository screeningRepository;

    @GetMapping
    public List<Customer> getAllCustomers(){
        return this.customerRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Customer> getById(@PathVariable("id") Integer id) {
        Customer customer = this.customerRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cant find "));

        return ResponseEntity.ok(customer);
    }


    //Get all tickets
    @GetMapping("{customerId}/screenings/{screeningId}")
    public List<Ticket> getAllTickets(@PathVariable(name="customerId") Integer customerId,
                                      @PathVariable(name="screeningId") Integer screeningId) {
        Customer customer = this.customerRepository.findById(customerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cant find the customer"));

        Screening screening = this.screeningRepository.findById(screeningId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cant find the screening"));

        //Combine both ticket lists and remove tickets that does not match
        List<Ticket> combinedList = new ArrayList<>(customer.getTickets());
        List<Ticket> screeningTickets = screening.getTickets();
        combinedList.retainAll(screeningTickets);
        return combinedList;
    }

    //Post for customer
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        //Dates
        LocalDateTime currentDateTime = LocalDateTime.now();
        customer.setCreatedAt(currentDateTime);
        customer.setUpdatedAt(null);
        Customer createdCustomer = this.customerRepository.save(customer);
        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
    }

    //Post for ticket
    @PostMapping("{customerId}/screenings/{screeningId}")
    public ResponseEntity<Ticket> createTicket(@PathVariable(name="customerId") Integer customerId,
                                               @PathVariable(name="screeningId") Integer screeningId,
                                               @RequestBody Ticket ticket) {
        Customer customer = this.customerRepository.findById(customerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cant find the customer"));
        ticket.setCustomer(customer);

        Screening screening = this.screeningRepository.findById(screeningId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cant find the screening"));
        ticket.setScreening(screening);

        //Dates
        LocalDateTime currentDateTime = LocalDateTime.now();
        ticket.setCreatedAt(currentDateTime);
        ticket.setUpdatedAt(null);
        Ticket createdTicket = this.ticketRepository.save(ticket);
        return new ResponseEntity<>(createdTicket, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateACustomer(@PathVariable int id,@RequestBody Customer customer){
        Customer customerToUpdate = this.customerRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cant find the customer...."));

        customerToUpdate.setName(customer.getName());
        customerToUpdate.setEmail(customer.getEmail());
        customerToUpdate.setPhone(customer.getPhone());

        //Set update to right now
        LocalDateTime currentDateTime = LocalDateTime.now();
        customerToUpdate.setUpdatedAt(currentDateTime);

        return new ResponseEntity<>(this.customerRepository.save(customerToUpdate),HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> deleteACustomer(@PathVariable int id){
        Customer customerToDelete = this.customerRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cant find the customer!!!"));
        this.customerRepository.delete(customerToDelete);
        return ResponseEntity.ok(customerToDelete);
    }
}