package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
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
    public List<Customer> getAllCustomers() {
        return this.customerRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        if (customer.getName() == null || customer.getEmail() == null || customer.getPhone() == null) {
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(this.customerRepository.save(customer), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> deleteCustomerById(@PathVariable int id) {
        Customer customerToDelete = getACustomer(id);
        this.customerRepository.delete(customerToDelete);
        return ResponseEntity.ok(customerToDelete);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomerById(@PathVariable int id, @RequestBody Customer customer) {
        if (customer.getName() == null || customer.getEmail() == null || customer.getPhone() == null) {
            return ResponseEntity.badRequest().build();
        }
        Customer customerToUpdate = getACustomer(id);
        customerToUpdate.setName(customer.getName());
        customerToUpdate.setEmail(customer.getEmail());
        customerToUpdate.setPhone(customer.getPhone());
        return new ResponseEntity<>(this.customerRepository.save(customerToUpdate), HttpStatus.CREATED);
    }

    /**
     * This should go to TicketController Class somehow.
     * Problem is that the Mapping from customers is occupied by this Controller.
     * Same for method two below.
     * @param customerId
     * @param ticket
     * @param screeningId
     * @return
     */
    @PostMapping("/{customerId}/screenings/{screeningId}")
    public ResponseEntity<Ticket> createTicket(@PathVariable int customerId, @RequestBody Ticket ticket, @PathVariable int screeningId) {
        Customer tempCustomer = getACustomer(customerId);
        Screening tempScreening = getAScreening(screeningId);
        ticket.setCustomer(tempCustomer);
        ticket.setScreening(tempScreening);
        ticket.setNumSeats(ticket.getNumSeats());
        ticket.setCreatedAt(new Date());
        return new ResponseEntity<>(this.ticketRepository.save(ticket), HttpStatus.CREATED);
    }

    @GetMapping("/{customerId}/screenings/{screeningId}")
    public ResponseEntity<List<Ticket>> getAllTicketsByCustomerId(@PathVariable int customerId, @PathVariable int screeningId) {
        List<Ticket> tempTicketList = new ArrayList<>();
        for(Ticket ticket : ticketRepository.findAll()) {
            if (ticket.getCustomer().getId() == customerId && ticket.getScreening().getId() == screeningId) {
                tempTicketList.add(ticket);
            }
        }
        if (tempTicketList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tempTicketList);
    }


    /**
     * Helper method
     * @param id
     * @return
     */
    private Customer getACustomer(int id) {
        return this.customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
    }

    private Screening getAScreening(int id) {
        return this.screeningRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
    }
}
