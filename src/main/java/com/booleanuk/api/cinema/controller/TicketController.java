package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.dto.TicketDto;
import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import com.booleanuk.api.cinema.response.ApiException;
import com.booleanuk.api.cinema.response.ApiSuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customers/{customerId}/screenings")
public class TicketController {
    @Autowired
    TicketRepository repository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ScreeningRepository screeningRepository;

    @GetMapping
    public ResponseEntity<ApiSuccessResponse<List<TicketDto>>> getAllTicketsForCustomer(@PathVariable int customerId) {
        List<TicketDto> tickets = this.repository.findByCustomerId(customerId);
        if (tickets.isEmpty()) {
            throw new ApiException(HttpStatus.NOT_FOUND, "not found");
        }
        return ResponseEntity.ok(new ApiSuccessResponse<>(tickets));
    }

    @GetMapping("/{screeningId}")
    public ResponseEntity<ApiSuccessResponse<List<TicketDto>>> getScreeningsForCustomer(@PathVariable int customerId, @PathVariable int screeningId) {
        List<TicketDto> tickets = this.repository.findByCustomerIdAndScreeningId(customerId, screeningId);
        if (tickets.isEmpty()) {
            throw new ApiException(HttpStatus.NOT_FOUND, "not found");
        }
        return ResponseEntity.ok(new ApiSuccessResponse<>(tickets));
    }

    @PostMapping("/{screeningId}")
    public ResponseEntity<ApiSuccessResponse<TicketDto>> bookTicket(@PathVariable int customerId, @PathVariable int screeningId, @RequestBody Ticket ticket) {
        if (ticket.getNumSeats() <= 0) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "bad request");
        }
        Customer customer = this.customerRepository.findById(customerId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "not found"));
        Screening screening = this.screeningRepository.findById(screeningId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "not found"));
        ticket.setScreening(screening);
        ticket.setCustomer(customer);
        return new ResponseEntity<>(new ApiSuccessResponse<>(this.translateToDto(this.repository.save(ticket))), HttpStatus.CREATED);
    }

    public TicketDto translateToDto(Ticket ticket) {
        return new TicketDto(ticket.getId(), ticket.getNumSeats(), ticket.getCreatedAt(), ticket.getUpdatedAt());
    }
}
