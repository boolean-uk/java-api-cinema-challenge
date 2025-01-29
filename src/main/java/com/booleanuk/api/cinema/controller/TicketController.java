package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import com.booleanuk.api.cinema.responses.ErrorResponse;
import com.booleanuk.api.cinema.responses.Response;
import com.booleanuk.api.cinema.responses.TicketListResponse;
import com.booleanuk.api.cinema.responses.TicketResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("customers/{cId}/screenings/{sId}")
public class TicketController {
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ScreeningRepository screeningRepository;


    @GetMapping
    public ResponseEntity<?> getAllTickets(@PathVariable("cId") int cId, @PathVariable("sId") int sId){
        Customer customer = this.customerRepository.findById(cId).orElse(null);
        Screening screening = this.screeningRepository.findById(sId).orElse(null);

        if(customer == null || screening == null){
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        TicketListResponse ticketListResponse = new TicketListResponse();
        ticketListResponse.set(this.ticketRepository.findAllTicketsByCustomerAndScreening(customer, screening));

        return ResponseEntity.ok(ticketListResponse);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Response<?>> create(@RequestBody Ticket request, @PathVariable("cId") int cId, @PathVariable("sId") int sId) {
        TicketResponse ticketResponse = new TicketResponse();

        Customer customer = this.customerRepository.findById(cId).orElse(null);
        Screening screening = this.screeningRepository.findById(sId).orElse(null);

        if(customer == null || screening == null){
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        request.setCustomer(customer);
        request.setScreening(screening);

        ticketResponse.set(this.ticketRepository.save(request));
        return new ResponseEntity<>(ticketResponse, HttpStatus.CREATED);
    }
}
