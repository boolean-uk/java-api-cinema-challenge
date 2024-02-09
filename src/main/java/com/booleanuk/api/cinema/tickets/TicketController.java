package com.booleanuk.api.cinema.tickets;

import com.booleanuk.api.cinema.customers.Customer;
import com.booleanuk.api.cinema.customers.CustomerRepository;
import com.booleanuk.api.cinema.screenings.Screening;
import com.booleanuk.api.cinema.screenings.ScreeningRepository;
import com.booleanuk.api.helpers.ResponseHandler;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("customers/{customerId}/screenings/{screeningId}")
public class TicketController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping
    public List<Ticket> getAll(@PathVariable int customerId, @PathVariable int screeningId){
        return this.ticketRepository.findAllByCustomerIdAndScreeningId(customerId, screeningId);
    }
    @PostMapping
    public ResponseEntity<Object> createOne(@PathVariable int customerId, @PathVariable int screeningId, @Valid @RequestBody Ticket ticket){
        try{
            Customer customer = this.customerRepository
                    .findById(customerId)
                    .orElseThrow(ChangeSetPersister.NotFoundException::new);

            Screening screening = this.screeningRepository.findById(screeningId)
                    .orElseThrow(ChangeSetPersister.NotFoundException::new);

            if(screening.getCapacity() < ticket.getNumSeats()){
                return ResponseHandler.generateError(HttpStatus.BAD_REQUEST);
            }
            screening.setCapacity(screening.getCapacity() - ticket.getNumSeats());
            ticket.setCustomer(customer);
            ticket.setScreening(screening);
            this.screeningRepository.save(screening);
            return ResponseHandler.generateResponse(HttpStatus.CREATED, this.ticketRepository.save(ticket));
        }
        catch (ChangeSetPersister.NotFoundException e){
            return ResponseHandler.generateError(HttpStatus.NOT_FOUND);
        }




    }
}
