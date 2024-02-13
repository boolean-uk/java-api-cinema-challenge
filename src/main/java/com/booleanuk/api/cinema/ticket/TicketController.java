package com.booleanuk.api.cinema.ticket;

import com.booleanuk.api.cinema.ApiResponse;
import com.booleanuk.api.cinema.ErrorResponse;
import com.booleanuk.api.cinema.customer.Customer;
import com.booleanuk.api.cinema.customer.CustomerRepository;
import com.booleanuk.api.cinema.movie.Movie;
import com.booleanuk.api.cinema.movie.MovieRepository;
import com.booleanuk.api.cinema.screening.Screening;
import com.booleanuk.api.cinema.screening.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("customers/{customerId}/screenings/{screeningId}")
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ScreeningRepository screeningRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getTickets(@PathVariable int customerId, @PathVariable int screeningId){

        Customer tempCustomer = getACustomer(customerId);
        Screening tempScreening = getAScreening(screeningId);

        if(tempCustomer == null && tempScreening == null){
            return this.notFound("customer or screening");
        } else if (tempCustomer == null) {
            return this.notFound("customer");
        } else if (tempScreening == null){
            return this.notFound("screening");
        }

        List<Ticket> tickets = new ArrayList<>();

        for (Ticket customerTicket : tempCustomer.getTickets()) {
            for (Ticket screeningTicket : tempScreening.getTickets()) {
                if (customerTicket.getId() == screeningTicket.getId()) {
                    tickets.add(customerTicket);
                }
            }
        }
        TicketListResponse ticketListResponse = new TicketListResponse();
        ticketListResponse.set(tickets);

        return new ResponseEntity<>(ticketListResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createTickets(@PathVariable int customerId, @PathVariable int screeningId, @RequestBody Ticket ticket){
        Customer tempCustomer = getACustomer(customerId);
        Screening tempScreening = getAScreening(screeningId);

        if(tempCustomer == null && tempScreening == null){
            return this.notFound("customer or screening");
        } else if (tempCustomer == null) {
            return this.notFound("customer");
        } else if (tempScreening == null){
            return this.notFound("screening");
        }

        if (tempScreening.getNumberOfBookedSeats() + ticket.getNumSeats() > tempScreening.getCapacity()){
            ErrorResponse error = new ErrorResponse();
            error.set("Theres only " + (tempScreening.getCapacity() - tempScreening.getNumberOfBookedSeats()) +
                    " seats left, you cant book " + ticket.getNumSeats() + " seats");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        ticket.setCustomer(tempCustomer);
        ticket.setScreening(tempScreening);
        Ticket createdTicket = this.ticketRepository.save(ticket);

        TicketResponse ticketResponse = new TicketResponse();
        ticketResponse.set(createdTicket);

        return new ResponseEntity<>(ticketResponse, HttpStatus.CREATED);
    }

    private Customer getACustomer(int id){
        return this.customerRepository.findById(id).orElse(null);
    }

    private Screening getAScreening(int id){
        return this.screeningRepository.findById(id).orElse(null);
    }

    private ResponseEntity<ApiResponse<?>> notFound(String type){
        ErrorResponse error = new ErrorResponse();
        String msg = "No " + type + " with that id were found";
        error.set(msg);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}
