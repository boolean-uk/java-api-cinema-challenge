package com.booleanuk.api.cinema.tickets;

import com.booleanuk.api.cinema.customers.Customer;
import com.booleanuk.api.cinema.customers.CustomerRepository;
import com.booleanuk.api.cinema.responses.ErrorResponse;
import com.booleanuk.api.cinema.responses.Response;
import com.booleanuk.api.cinema.responses.TicketListResponse;
import com.booleanuk.api.cinema.responses.TicketResponse;
import com.booleanuk.api.cinema.screenings.Screening;
import com.booleanuk.api.cinema.screenings.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("customers/{customerId}/screenings/{screeningId}")
public class TicketController {
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private ErrorResponse errorResponse = new ErrorResponse();
    private TicketResponse ticketResponse = new TicketResponse();
    private TicketListResponse ticketListResponse = new TicketListResponse();

    @GetMapping
    public ResponseEntity<Response<?>> getAllTickets(@PathVariable int customerId, @PathVariable int screeningId) {
        Customer customer = this.customerRepository.findById(customerId).orElse(null);

        Screening screening = this.screeningRepository.findById(screeningId).orElse(null);

        if (customer == null || screening == null) {
            this.errorResponse.set("No ticket found for the customer and screening with those ids");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        List<Ticket> tickets = this.ticketRepository.findByCustomerAndScreening(customer, screening); //findALL?
        this.ticketListResponse.set(tickets);
        return ResponseEntity.ok(ticketListResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createNewTicket(@PathVariable int customerId, @PathVariable int screeningId, @RequestBody Ticket ticket) {
        Customer customer = this.customerRepository.findById(customerId).orElse(null);

        Screening screening = this.screeningRepository.findById(screeningId).orElse(null);

        if (customer == null || screening == null) {
            this.errorResponse.set("No customer or screening with those ids found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setUpdatedAt(LocalDateTime.now());
        ticket.setCustomer(customer);
        ticket.setScreening(screening);
        this.ticketRepository.save(ticket);
        this.ticketResponse.set(ticket);
        return new ResponseEntity<>(ticketResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("{ticket_id}")
    public ResponseEntity<Response<?>> deleteTicket(@PathVariable int id) {
        Ticket ticketToDelete = this.ticketRepository.findById(id).orElse(null);
        if (ticketToDelete == null) {
            errorResponse.set("No ticket with that id was found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        this.ticketRepository.delete(ticketToDelete);
        this.ticketResponse.set(ticketToDelete);
        return ResponseEntity.ok(ticketResponse);
    }

}