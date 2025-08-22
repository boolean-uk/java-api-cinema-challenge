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

@RestController
@RequestMapping("customer/{cId}/screenings/{sId}")
public class TicketController {

    @Autowired
    private  CustomerRepository customerRepository;
    @Autowired
    private  ScreeningRepository screeningRepository;
    @Autowired
    private  TicketRepository ticketRepository;

    @GetMapping
    public ResponseEntity<?> getAllByCustomerAndScreening(@PathVariable("cId") int cId, @PathVariable("sId") int sId) {
        TicketListResponse ticketListResponse = new TicketListResponse();
        Customer customer = this.customerRepository.findById(cId).orElse(null);
        Screening screening = this.screeningRepository.findById(sId).orElse(null);

        if (customer == null || screening == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Customer or screening not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        ticketListResponse.set(this.ticketRepository.findAllTicketsByScreeningAndCustomer(screening, customer));
        return ResponseEntity.ok(ticketListResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> book(@RequestBody Ticket ticket, @PathVariable("cId") int cId, @PathVariable("sId") int sId) {
        Customer theCustomer = this.customerRepository.findById(cId).orElse(null);
        if (theCustomer == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Customer not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        ticket.setCustomer(theCustomer);
        Screening theScreening = this.screeningRepository.findById(sId).orElse(null);
        if (theScreening == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Screening not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        ticket.setScreening(theScreening);
        TicketResponse ticketResponse = new TicketResponse();
        try {
            ticketResponse.set(this.ticketRepository.save(ticket));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ticketResponse, HttpStatus.CREATED);
    }
}
