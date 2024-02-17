package com.booleanuk.api.cinema.customer;

import com.booleanuk.api.cinema.response.*;
import com.booleanuk.api.cinema.screening.Screening;
import com.booleanuk.api.cinema.screening.ScreeningRepository;
import com.booleanuk.api.cinema.ticket.Ticket;
import com.booleanuk.api.cinema.ticket.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ScreeningRepository screeningRepository;
    @Autowired
    private TicketRepository ticketRepository;

    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
        if(customer.getName() == null || customer.getEmail() == null || customer.getPhone() == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Could not create a new customer, please check all fields are correct.");

            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        Customer newCustomer = this.customerRepository.save(customer);

        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.set(newCustomer);

        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<CustomerListResponse> getAllCustomers() {
        List<Customer> allCustomers = this.customerRepository.findAll();

        CustomerListResponse customerListResponse = new CustomerListResponse();
        customerListResponse.set(allCustomers);

        return ResponseEntity.ok(customerListResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        Customer customerToBeUpdated = this.customerRepository.findById(id).orElse(null);

        if(customerToBeUpdated == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No customer with that id found.");

            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        // "If any field is not provided, the original value should not be changed. Any combination of fields can be updated."
        if(customer.getName() != null) {
            customerToBeUpdated.setName(customer.getName());
        }
        if(customer.getEmail() != null) {
            customerToBeUpdated.setEmail(customer.getEmail());
        }
        if(customer.getPhone() != null) {
            customerToBeUpdated.setPhone(customer.getPhone());
        }

        Customer updatedCustomer = this.customerRepository.save(customerToBeUpdated);

        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.set(updatedCustomer);

        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable int id) {
        Customer customerToBeDeleted = this.customerRepository.findById(id).orElse(null);

        if(customerToBeDeleted == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No customer with that id found.");

            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        this.customerRepository.deleteById(id);

        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.set(customerToBeDeleted);

        return ResponseEntity.ok(customerToBeDeleted);
    }

    @PostMapping("/{customerId}/screenings/{screeningId}")
    public ResponseEntity<?> createTicket(@PathVariable int customerId, @PathVariable int screeningId, @RequestBody Ticket ticket) {
        Customer customer = this.customerRepository.findById(customerId).orElse(null);

        Screening screening = this.screeningRepository.findById(screeningId).orElse(null);

        if(customer == null || screening == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No customer or screening with those ids found.");

            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        ticket.setCustomer(customer);
        ticket.setScreening(screening);

        Ticket newTicket = this.ticketRepository.save(ticket);

        List<Ticket> customerTickets = customer.getTickets();
        customerTickets.add(newTicket);
        customer.setTickets(customerTickets);

        List<Ticket> screeningTickets = screening.getTickets();
        screeningTickets.add(newTicket);
        customer.setTickets(screeningTickets);

        TicketResponse ticketResponse = new TicketResponse();
        ticketResponse.set(newTicket);

        return new ResponseEntity<>(ticketResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{customerId}/screenings/{screeningId}")
    public ResponseEntity<?> getAllTickets(@PathVariable int customerId, @PathVariable int screeningId) {
        List<Ticket> allTicketsACustomerHasBookedForAScreening = new ArrayList<>();

        Customer customer = this.customerRepository.findById(customerId).orElse(null);

        if(customer == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No customer with that id found.");

            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        List<Ticket> customersTickets = customer.getTickets();

        for(Ticket ticket : customersTickets) {
            if(ticket.getScreening().getId() == screeningId) {
                allTicketsACustomerHasBookedForAScreening.add(ticket);
            }
        }

        TicketListResponse ticketListResponse = new TicketListResponse();
        ticketListResponse.set(allTicketsACustomerHasBookedForAScreening);

        return ResponseEntity.ok(ticketListResponse);
    }
}
