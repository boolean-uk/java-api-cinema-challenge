package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.CustomResponse;
import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class TicketController {
    private final TicketRepository ticketRepository;
    private final ScreeningRepository screeningRepository;
    private final CustomerRepository customerRepository;

    public TicketController(TicketRepository ticketRepository,
                            ScreeningRepository screeningRepository,
                            CustomerRepository customerRepository) {
        this.ticketRepository = ticketRepository;
        this.screeningRepository = screeningRepository;
        this.customerRepository = customerRepository;
    }

    @GetMapping("customers/{customer_id}/screenings/{screening_id}")
    public ResponseEntity<CustomResponse> findAll(@PathVariable long customer_id, @PathVariable long screening_id) {
        CustomResponse response = new CustomResponse("success",
                this.ticketRepository.findByCustomer_IdAndScreening_Id(customer_id, screening_id));
        return ResponseEntity.ok(response);
    }

    @PostMapping("customers/{customer_id}/screenings/{screening_id}")
    public ResponseEntity<CustomResponse> createScreening(@PathVariable long customer_id, @PathVariable long screening_id,
                                                     @RequestBody Ticket ticket) {
        Optional<Customer> customerOptional = this.customerRepository.findById(customer_id);
        Optional<Screening> screeningOptional = this.screeningRepository.findById(screening_id);
        if (customerOptional.isEmpty() || screeningOptional.isEmpty())
            return ResponseEntity.notFound().build();

        ticket.setCustomer(customerOptional.get());
        ticket.setScreening(screeningOptional.get());

        CustomResponse response = new CustomResponse("success",
                this.ticketRepository.save(ticket));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
