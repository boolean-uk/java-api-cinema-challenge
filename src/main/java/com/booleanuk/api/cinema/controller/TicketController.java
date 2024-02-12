
package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.helpers.CustomResponse;
import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("customers")
public class TicketController {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ScreeningRepository screeningRepository;
    @Autowired
    TicketRepository ticketRepository;

    @GetMapping("/{customerId}/screenings/{screeningId}")
    public ResponseEntity<CustomResponse> getAll(@PathVariable int customerId, @PathVariable int screeningId){
        if(!customerRepository.existsById(customerId)){
            return new ResponseEntity<>(new CustomResponse("error", "No customer with that id were found"), HttpStatus.NOT_FOUND);
        }
        if(!screeningRepository.existsById(screeningId)){
            return new ResponseEntity<>(new CustomResponse("error", "No screening with that id were found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new CustomResponse("success", ticketRepository.findAll()), HttpStatus.OK);
    }

    @PostMapping("/{customer_id}/screenings/{screening_id}")
    public ResponseEntity<CustomResponse> create(@PathVariable int customer_id, @PathVariable int screening_id, @RequestBody Ticket ticket){
        if(!customerRepository.existsById(customer_id)){
            return new ResponseEntity<>(new CustomResponse("error", "No customer with that id were found"), HttpStatus.NOT_FOUND);
        }
        if(!screeningRepository.existsById(screening_id)){
            return new ResponseEntity<>(new CustomResponse("error", "No screening with that id were found"), HttpStatus.NOT_FOUND);
        }
        if(ticket.getNumSeats() == 0){
            return new ResponseEntity<>(new CustomResponse("error", "Could not create ticket, please check all required fields are correct"), HttpStatus.BAD_REQUEST);
        }
        ticket.setCustomer(customerRepository.findById(customer_id).get());
        ticket.setScreening(screeningRepository.findById(screening_id).get());
        return new ResponseEntity<>(new CustomResponse("success", ticketRepository.save(ticket)), HttpStatus.CREATED);
    }

}
