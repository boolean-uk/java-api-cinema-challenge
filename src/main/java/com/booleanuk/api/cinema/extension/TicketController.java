package com.booleanuk.api.cinema.extension;


import com.booleanuk.api.cinema.models.Customer;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.repositories.CustomerRepository;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
    private DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd | HH:mm");


    @GetMapping
    public List<Ticket> getAllTicketsPerCustomer(@PathVariable int customer_id,
                                      @PathVariable int screening_id){
        Screening tempScreening = findScreening(screening_id);
        Customer tempCustomer = findCustomer(customer_id);

        return this.ticketRepository.findAllByCustomerAndScreening(tempCustomer, tempScreening);
    }

    @PostMapping
    public ResponseEntity<Ticket> createTicketForCustomer(@PathVariable int customer_id,
                                                          @PathVariable int screening_id,
                                                          @RequestBody Ticket ticket){
        today = LocalDateTime.now();

        Customer tempCustomer = findCustomer(customer_id);
        Screening tempScreening = findScreening(screening_id);
        ticket.setCustomer(tempCustomer);
        ticket.setScreening(tempScreening);
        ticket.setCreatedAt(today.format(pattern));

        return new ResponseEntity<Ticket>(this.ticketRepository.save(ticket),
                HttpStatus.CREATED);
    }



    private Customer findCustomer(int id){
        return this.customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private Screening findScreening(int id){
        return this.screeningRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


}
