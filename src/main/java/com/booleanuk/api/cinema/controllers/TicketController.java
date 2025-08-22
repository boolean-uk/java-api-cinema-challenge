package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Customer;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.models.Ticket;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import com.booleanuk.api.cinema.response.ErrorResponse;
import com.booleanuk.api.cinema.response.Response;
import com.booleanuk.api.cinema.response.TicketListResponse;
import com.booleanuk.api.cinema.response.TicketResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("customers")
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private TicketResponse ticketResponse = new TicketResponse();
    private TicketListResponse ticketListResponse = new TicketListResponse();

    @PostMapping("{customerId}/screenings/{screeningId}")
        public ResponseEntity<Response<?>> createTicket(@PathVariable(name = "customerId") int customerId, @PathVariable(name = "screeningId") int screeningId, @RequestBody Ticket ticket){

            Customer customer = this.customerRepository.findById(customerId).orElse(null);
            Screening screening = this.screeningRepository.findById(screeningId).orElse(null);

            if (customer == null || screening == null){
                ErrorResponse error = new ErrorResponse();
                error.set("No customer or screening with those ids found");

                new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }

            ticket.setCustomer(customer);
            ticket.setScreening(screening);
            ticket.setCreatedAt(LocalDateTime.now());
            ticket.setUpdatedAt(LocalDateTime.now());

            this.ticketRepository.save(ticket);
            this.ticketResponse.set(ticket);

            return new ResponseEntity<>(ticketResponse, HttpStatus.CREATED);
    }

    @GetMapping("{customerId}/screenings/{screeningId}")
    public ResponseEntity<Response<?>> getAllTickets (@PathVariable(name = "customerId") int customerId, @PathVariable(name = "screeningId") int screeningId){

        Customer customer = this.customerRepository.findById(customerId).orElse(null);
        Screening screening = this.screeningRepository.findById(screeningId).orElse(null);
        if (customer == null || screening == null){
            ErrorResponse error = new ErrorResponse();
            error.set("No customer or screening with those ids found");

            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        List<Ticket> ticketList = this.ticketRepository.findAllByCustomerIdAndScreeningId(customer.getId(), screening.getId());
        if (ticketList.isEmpty()){
            ErrorResponse error = new ErrorResponse();
            error.set("No ticket found for the customer and screening with those ids found");

            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        this.ticketListResponse.set(ticketList);
        return ResponseEntity.ok(ticketListResponse);
    }

}
