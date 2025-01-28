package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import com.booleanuk.api.cinema.responses.ErrorResponse;
import com.booleanuk.api.cinema.responses.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private CustomerRepository customerRepository;
    private TicketRepository ticketRepository;
    private ScreeningRepository screeningRepository;

    public CustomerController(CustomerRepository customerRepository, TicketRepository ticketRepository, ScreeningRepository screeningRepository) {
        this.customerRepository = customerRepository;
        this.ticketRepository = ticketRepository;
        this.screeningRepository = screeningRepository;
    }

    @PostMapping
    public ResponseEntity<Response<?>> create(@RequestBody Customer customer) {
        Customer addedCustomer = customerRepository.save(customer);
        if (addedCustomer == null) {
            return new ResponseEntity<>(new ErrorResponse("bad request"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new Response<>("success", addedCustomer), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getOne(@PathVariable int id) {
//        return ResponseEntity.ok(customerRepository.findById(id).orElseThrow(
//                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No customer with that ID was found.")
//        ));
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer == null) {
            ErrorResponse error = new ErrorResponse("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        Response<Customer> response = new Response<>();
        response.set(customer);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Response<?>> getAll() {
        return ResponseEntity.ok(new Response<>("success", customerRepository.findAll()));

    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> update(@PathVariable int id, @RequestBody Customer customer) {
        Customer customerToUpdate = customerRepository.findById(id).orElse(null);
        if (customerToUpdate == null) {
            return new ResponseEntity<>(new ErrorResponse("not found"), HttpStatus.NOT_FOUND);
        }
        customerToUpdate.setName(customer.getName());
        customerToUpdate.setEmail(customer.getEmail());
        customerToUpdate.setPhone(customer.getPhone());
        customerToUpdate.setUpdatedAt(LocalDateTime.now());
        // todo error 400
        return new ResponseEntity<>(new Response<>(customerRepository.save(customerToUpdate)), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteCustomer(@PathVariable int id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer == null) {
            return new ResponseEntity(new ErrorResponse("not found"), HttpStatus.NOT_FOUND);
        }

        customerRepository.deleteById(id);
        return ResponseEntity.ok(new Response<>("success", customer));
    }

    @PostMapping("/{c_id}/screenings/{s_id}")
    public ResponseEntity<Response<?>> bookTicket(@PathVariable(name = "c_id") int customerId, @PathVariable(name = "s_id") int screeningId, @RequestBody Ticket ticket) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        Screening screening = screeningRepository.findById(screeningId).orElse(null);

        if (customer == null || screening == null) {
            return new ResponseEntity<>(new ErrorResponse("not found"), HttpStatus.NOT_FOUND);
        }

        ticket.setCustomer(customer);
        ticket.setScreening(screening);
        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setUpdatedAt(LocalDateTime.now());
        return new ResponseEntity<>(new Response<>("success", ticketRepository.save(ticket)), HttpStatus.CREATED);
    }

    @GetMapping("/{c_id}/screenings/{s_id}")
    public ResponseEntity<Response<?>> getAllTickets(@PathVariable(name = "c_id") int customerId, @PathVariable(name = "s_id") int screeningId) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        Screening screening = screeningRepository.findById(screeningId).orElse(null);

        if (customer == null || screening == null) {
            return new ResponseEntity<>(new ErrorResponse("not found"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new Response<>("success", ticketRepository.findAll().stream().filter(
                t -> t.getCustomerId() == customer.getId() && t.getScreeningId() == screening.getId()
        ).toList()), HttpStatus.OK);
    }
}
