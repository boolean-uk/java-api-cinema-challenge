package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import com.booleanuk.api.cinema.response.*;
import com.booleanuk.api.cinema.response.Error;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ScreeningRepository screeningRepository;
    @Autowired
    private TicketRepository ticketRepository;
    private LocalDateTime time = LocalDateTime.now();


    @GetMapping
    public ResponseEntity<Response> getAllCustomers() {
        List<Customer> movies = this.customerRepository.findAll();
        return new ResponseEntity<>(new CustomerListResponse(this.customerRepository.findAll()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Response> createCustomer(@RequestBody Customer customer) {
        String nameRegex = "^(([a-zA-z])+(\\s)*)+$";
        String emailRegex = "^[^@]+@[^@]+\\.[^@]+$";
        String phoneRegex = "^(\\+[0-9]{2})*([0-9]{8})$";
        if(customer.getName().matches(nameRegex) && customer.getEmail().matches(emailRegex) && customer.getPhone().matches(phoneRegex)) {
            customer.setCreatedAt(time);
            customer.setUpdatedAt(time);
            Customer newCustomer = this.customerRepository.save(customer);
            newCustomer.setTickets(new ArrayList<>());
            return new ResponseEntity<>(new CustomerResponse(newCustomer), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new ErrorResponse(new Error("bad request")), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        String nameRegex = "^(([a-zA-z])+(\\s)*)+$";
        String emailRegex = "^[^@]+@[^@]+\\.[^@]+$";
        String phoneRegex = "^(\\+[0-9]{2})*([0-9]{8})$";
        if(customer.getName().matches(nameRegex) && customer.getEmail().matches(emailRegex) && customer.getPhone().matches(phoneRegex)) {
            Customer customerToUpdate = customerRepository.findById(id)
                    .orElse(null);
            if(customerToUpdate == null) {
                return new ResponseEntity<>(new ErrorResponse(new Error("not found")), HttpStatus.NOT_FOUND);
            }
            customerToUpdate.setUpdatedAt(time);
            customerToUpdate.setName(customer.getName());
            customerToUpdate.setEmail(customer.getEmail());
            customerToUpdate.setPhone(customer.getPhone());
            return new ResponseEntity<>(new CustomerResponse(customerToUpdate), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new ErrorResponse(new Error("bad request")), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteCustomer(@PathVariable int id) {
        Customer customerToDelete = customerRepository.findById(id)
                .orElse(null);
        if(customerToDelete == null) {
            return new ResponseEntity<>(new ErrorResponse(new Error("not found")), HttpStatus.NOT_FOUND);
        }
        if(!customerToDelete.getTickets().isEmpty()) {
            return new ResponseEntity<>(new ErrorResponse(new Error("Customer has tickets, and could not be deleted")), HttpStatus.BAD_REQUEST);
        }
        this.customerRepository.delete(customerToDelete);
        customerToDelete.setTickets(new ArrayList<>());
        return new ResponseEntity<>(new CustomerResponse(customerToDelete), HttpStatus.OK);
    }


    @GetMapping("/{customerId}/screenings/{screeningId}")
    public ResponseEntity<Response> getAllTicketsFromScreening(@PathVariable int customerId, @PathVariable int screeningId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        Screening screening = screeningRepository.findById(screeningId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        List<Ticket> customerTickets = customer.getTickets();
        List<Ticket> screeningTickets = screening.getTickets();
        List<Ticket> combinedTickets = new ArrayList<>(customerTickets);
        combinedTickets.retainAll(screeningTickets);
        if(!combinedTickets.isEmpty()) {
            return new ResponseEntity<>(new TicketListResponse(combinedTickets), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ErrorResponse(new Error("not found")), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{customerId}/screenings/{screeningId}")
    public ResponseEntity<Response> createTicket(@PathVariable int customerId, @PathVariable int screeningId, @RequestBody Ticket ticket) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        Screening screening = screeningRepository.findById(screeningId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        ticket.setCreatedAt(time);
        ticket.setUpdatedAt(time);
        ticket.setCustomer(customer);
        ticket.setScreening(screening);
        return new ResponseEntity<>(new TicketResponse(this.ticketRepository.save(ticket)), HttpStatus.CREATED);
    }

}
