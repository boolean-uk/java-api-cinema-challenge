package com.booleanuk.api.cinema.extension;


import com.booleanuk.api.cinema.models.Customer;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.repositories.CustomerRepository;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import com.booleanuk.api.cinema.responses.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("customers/{customer_id}/screenings/{screening_id}")
public class TicketController {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ScreeningRepository screeningRepository;

    private LocalDateTime today;


    @GetMapping
    public ResponseEntity<Response<?>> getAllTicketsPerCustomer(@PathVariable int customer_id,
                                                             @PathVariable int screening_id){
        Screening tempScreening = findScreening(screening_id);
        Customer tempCustomer = findCustomer(customer_id);

        if (tempScreening == null){
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
        }

        if (tempCustomer == null){
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
        }
        CustomerResponse customerResponse = new CustomerResponse();
        ScreeningResponse screeningResponse = new ScreeningResponse();

        List<Ticket> tickets = this.ticketRepository.findAllByCustomerAndScreening(tempCustomer, tempScreening);
        TicketListResponse ticketListResponse = new TicketListResponse();
        ticketListResponse.set(tickets);
        return ResponseEntity.ok(ticketListResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createTicketForCustomer(@PathVariable int customer_id,
                                                          @PathVariable int screening_id,
                                                          @RequestBody Ticket ticket){
        today = LocalDateTime.now();

        Customer tempCustomer = findCustomer(customer_id);
        Screening tempScreening = findScreening(screening_id);

        if (tempCustomer == null){
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
        }

        if (tempScreening == null){
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
        }

        if (ticket.getNumSeats() < 1){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("bad request");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        ticket.setCustomer(tempCustomer);
        ticket.setScreening(tempScreening);
        ticket.setCreatedAt(String.valueOf(today));

        TicketResponse ticketResponse = new TicketResponse();
        ticketResponse.set(this.ticketRepository.save(ticket));

        return new ResponseEntity<>(ticketResponse,HttpStatus.CREATED);
    }



    private Customer findCustomer(int id){
        return this.customerRepository.findById(id)
                .orElse(null);
    }

    private Screening findScreening(int id){
        return this.screeningRepository.findById(id)
                .orElse(null);
    }


}
