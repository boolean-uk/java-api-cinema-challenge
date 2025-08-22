package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.*;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("customers")
public class CustomerController {
    @Autowired
    private final CustomerRepository repository;

    @Autowired
    private final TicketRepository tRepository;

    @Autowired
    private final ScreeningRepository sRepository;

    public CustomerController(CustomerRepository repository, TicketRepository ticketRepository, ScreeningRepository screeningRepository) {
        this.repository = repository;
        this.tRepository = ticketRepository;
        this.sRepository = screeningRepository;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> create(@RequestBody Customer customerDetails) {
        if (!isValidCustomer(customerDetails)) {
            ApiResponse<String> response = new ApiResponse<>("error", "Could not create a new customer, please check that all fields are correct.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Customer customer = new Customer(customerDetails.getName(), customerDetails.getEmail(), customerDetails.getPhone());
        ApiResponse<Customer> response = new ApiResponse<>("success", repository.save(customer));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("{customerId}/screenings/{screeningId}")
    public ResponseEntity<ApiResponse<?>> createTicket(@PathVariable int customerId, @PathVariable int screeningId, @RequestBody Ticket ticketDetails) {
        if (!isValidTicket(ticketDetails)) {
            ApiResponse<String> response = new ApiResponse<>("error", "Could not create a ticket with the specified parameters, please check all fields are correct");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<Customer> cus = this.repository.findById(customerId);

        if (cus.isEmpty()) {
            ApiResponse<String> response = new ApiResponse<>("error", String.format("No customer with id %d found.", customerId));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Optional<Screening> scr = this.sRepository.findById(screeningId);

        if (scr.isEmpty()) {
            ApiResponse<String> response = new ApiResponse<>("error", String.format("No screening with id %d found.", screeningId));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Customer customer = cus.get();
        Screening screening = scr.get();
        Ticket ticket = new Ticket(ticketDetails.getNumSeats(), customer, screening);

        ApiResponse<Ticket> response = new ApiResponse<>("success", this.tRepository.save(ticket));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Customer>>> readAll() {
        List<Customer> customers = this.repository.findAll();
        ApiResponse<List<Customer>> response = new ApiResponse<>("success", customers);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("{customerId}/screenings/{screeningId}")
    public ResponseEntity<ApiResponse<?>> readAllTickets(@PathVariable int customerId, @PathVariable int screeningId) {
        Optional<Customer> cus = this.repository.findById(customerId);

        if (cus.isEmpty()) {
            ApiResponse<String> response = new ApiResponse<>("error", String.format("No customer with id %d found.", customerId));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Optional<Screening> scr = this.sRepository.findById(screeningId);

        if (scr.isEmpty()) {
            ApiResponse<String> response = new ApiResponse<>("error", String.format("No screening with id %d found.", screeningId));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        List<Ticket> tickets = this.tRepository.findByCustomerIdAndScreeningId(customerId, screeningId);
        ApiResponse<List<Ticket>> response = new ApiResponse<>("success", tickets);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<?>> update(@PathVariable int id, @RequestBody Customer customerDetails) {
        if (!isValidCustomer(customerDetails)) {
            ApiResponse<String> response = new ApiResponse<>("error", "Could not create a new customer, please check that all fields are correct.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<Customer> cus = this.repository.findById(id);

        if (cus.isEmpty()) {
            ApiResponse<String> response = new ApiResponse<>("error", String.format("No customer with id %d found.", id));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Customer customer = cus.get();
        customer.setName(customerDetails.getName());
        customer.setEmail(customerDetails.getEmail());
        customer.setPhone(customerDetails.getPhone());
        customer.setUpdatedAt(OffsetDateTime.now());

        ApiResponse<Customer> response = new ApiResponse<>("success", repository.save(customer));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable int id) {
        Optional<Customer> cus = this.repository.findById(id);

        if (cus.isEmpty()) {
            ApiResponse<String> response = new ApiResponse<>("error", String.format("No customer with id %d found.", id));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Customer customer = cus.get();
        repository.delete(customer);

        ApiResponse<Customer> response = new ApiResponse<>("success", customer);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public boolean isValidCustomer(Customer customer) {
        return !(StringUtils.isBlank(customer.getName())
                || StringUtils.isBlank(customer.getEmail())
                || StringUtils.isBlank(customer.getPhone()));
    }

    public boolean isValidTicket(Ticket ticket) {
        return ticket.getNumSeats() != null;
    }
}
