package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import com.booleanuk.api.cinema.response.*;
import com.booleanuk.api.cinema.response.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private ScreeningRepository screeningRepository;

    @GetMapping
    public ResponseEntity<Response> getAllCustomers(){
        return new ResponseEntity<>(new CustomerListResponse(this.customerRepository.findAll()), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Response> getById(@PathVariable("id") Integer id) {
        Customer customer = this.customerRepository.findById(id).orElse(null);
        if (customer ==null){
            return new ResponseEntity<>(new ErrorResponse(new Error("Customer not found")),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new CustomerResponse(customer), HttpStatus.OK);
    }

    //Get all tickets
    @GetMapping("{customerId}/screenings/{screeningId}")
    public ResponseEntity<Response> getAllTickets(@PathVariable(name="customerId") Integer customerId,
                                      @PathVariable(name="screeningId") Integer screeningId) {

        Customer customer = this.customerRepository.findById(customerId).orElse(null);
        if (customer ==null){
            return new ResponseEntity<>(new ErrorResponse(new Error("Customer not found")),HttpStatus.NOT_FOUND);
        }
        Screening screening = this.screeningRepository.findById(screeningId).orElse(null);
        if (screening ==null){
            return new ResponseEntity<>(new ErrorResponse(new Error("Screening not found")),HttpStatus.NOT_FOUND);
        }
        //Combine both ticket lists and remove tickets that does not match
        List<Ticket> combinedList = new ArrayList<>(customer.getTickets());
        List<Ticket> screeningTickets = screening.getTickets();
        combinedList.retainAll(screeningTickets);
        return new ResponseEntity<>(new TicketListResponse(combinedList), HttpStatus.OK);

    }

    //Post for customer
    @PostMapping
    public ResponseEntity<Response> createCustomer(@RequestBody Customer customer) {
        //Dates
        LocalDateTime currentDateTime = LocalDateTime.now();
        customer.setCreatedAt(currentDateTime);
        customer.setUpdatedAt(null);

        //Regex to make sure the names are strings
        String regexName = "^[a-zA-Z\\s]+$";
        if(!customer.getName().matches(regexName)){
            return new ResponseEntity<>(new ErrorResponse(new Error("Bad request")),HttpStatus.BAD_REQUEST);
        }
        //Regex for email
        String regexEmail = "^[^@]+@[^@]+\\.[^@]+$";
        if(!customer.getEmail().matches(regexEmail)){
            return new ResponseEntity<>(new ErrorResponse(new Error("Bad request")),HttpStatus.BAD_REQUEST);
        }
        //Regex for norwegian phonenumber
        String regexPhone = "^(\\+[0-9]{2})*([0-9]{8})$";
        if(!customer.getPhone().matches(regexPhone)){
            return new ResponseEntity<>(new ErrorResponse(new Error("Bad request")),HttpStatus.BAD_REQUEST);
        }
        Customer createdCustomer = this.customerRepository.save(customer);

        return new ResponseEntity<>(new CustomerResponse(customer), HttpStatus.CREATED);
    }

    //Post for ticket
    @PostMapping("{customerId}/screenings/{screeningId}")
    public ResponseEntity<Response> createTicket(@PathVariable(name="customerId") Integer customerId,
                                               @PathVariable(name="screeningId") Integer screeningId,
                                               @RequestBody Ticket ticket) {
        Customer customer = this.customerRepository.findById(customerId).orElse(null);
        if (customer ==null){
            return new ResponseEntity<>(new ErrorResponse(new Error("Customer not found")),HttpStatus.NOT_FOUND);
        }
        Screening screening = this.screeningRepository.findById(screeningId).orElse(null);
        if (screening ==null){
            return new ResponseEntity<>(new ErrorResponse(new Error("Screening not found")),HttpStatus.NOT_FOUND);
        }
        ticket.setCustomer(customer);
        ticket.setScreening(screening);

        //Dates
        LocalDateTime currentDateTime = LocalDateTime.now();
        ticket.setCreatedAt(currentDateTime);
        ticket.setUpdatedAt(null);
        Ticket createdTicket = this.ticketRepository.save(ticket);
        return new ResponseEntity<>(new TicketResponse(createdTicket), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateACustomer(@PathVariable int id,@RequestBody Customer customer){
        Customer customerToUpdate = this.customerRepository.findById(id).orElse(null);
        if (customer ==null){
            return new ResponseEntity<>(new ErrorResponse(new Error("Customer not found")),HttpStatus.NOT_FOUND);
        }
        //Regex to make sure the names are strings
        String regexName = "^[a-zA-Z\\s]+$";
        if(!customer.getName().matches(regexName)){
            return new ResponseEntity<>(new ErrorResponse(new Error("Bad request")),HttpStatus.BAD_REQUEST);
        }
        //Regex for email
        String regexEmail =  "^[^@]+@[^@]+\\.[^@]+$";
        if(!customer.getEmail().matches(regexEmail)){
            return new ResponseEntity<>(new ErrorResponse(new Error("Bad request")),HttpStatus.BAD_REQUEST);
        }
        //Regex for norwegian phonenumber
        String regexPhone = "^(\\+[0-9]{2})*([0-9]{8})$";
        if(!customer.getPhone().matches(regexPhone)){
            return new ResponseEntity<>(new ErrorResponse(new Error("Bad request")),HttpStatus.BAD_REQUEST);
        }

        customerToUpdate.setName(customer.getName());
        customerToUpdate.setEmail(customer.getEmail());
        customerToUpdate.setPhone(customer.getPhone());

        //Set update to right now
        LocalDateTime currentDateTime = LocalDateTime.now();
        customerToUpdate.setUpdatedAt(currentDateTime);
        this.customerRepository.save(customerToUpdate);
        return new ResponseEntity<>(new CustomerResponse(customer), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteACustomer(@PathVariable int id){
        Customer customerToDelete = this.customerRepository.findById(id).orElse(null);
        if (customerToDelete ==null){
            return new ResponseEntity<>(new ErrorResponse(new Error("Customer not found")),HttpStatus.NOT_FOUND);
        }
        this.customerRepository.delete(customerToDelete);
        return new ResponseEntity<>(new CustomerResponse(customerToDelete), HttpStatus.OK);
    }
}