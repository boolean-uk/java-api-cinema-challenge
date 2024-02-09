package com.booleanuk.api.cinema.customer;

import com.booleanuk.api.cinema.screening.Screening;
import com.booleanuk.api.cinema.screening.ScreeningRepository;
import com.booleanuk.api.cinema.ticket.Ticket;
import com.booleanuk.api.cinema.ticket.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        validateCustomerOrThrowException(customer);

        Customer newCustomer = this.customerRepository.save(customer);

        return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(this.customerRepository.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        //validateCustomerOrThrowException(customer);

        if(customer.getName() == null && customer.getEmail() == null && customer.getPhone() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update customer, all provided fields are null.");
        }

        Customer customerToBeUpdated = findCustomerOrThrowException(id);

        // If any field is not provided, the original value should not be changed. Any combination of fields can be updated.
        if(customer.getName() != null) {
            customerToBeUpdated.setName(customer.getName());
        }
        if(customer.getEmail() != null) {
            customerToBeUpdated.setEmail(customer.getEmail());
        }
        if(customer.getPhone() != null) {
            customerToBeUpdated.setPhone(customer.getPhone());
        }

        this.customerRepository.save(customerToBeUpdated);

        return ResponseEntity.ok(customerToBeUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable int id) {
        Customer customerToBeDeleted = findCustomerOrThrowException(id);

        this.customerRepository.deleteById(id);

        return ResponseEntity.ok(customerToBeDeleted);
    }

    @PostMapping("/{customerId}/screenings/{screeningId}")
    public ResponseEntity<Ticket> createTicket(@PathVariable int customerId, @PathVariable int screeningId, @RequestBody Ticket ticket) {
        if(ticket.getNumSeats() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ticket field numSeats cannot be less than 0.");
        }

        Customer customer = findCustomerOrThrowException(customerId);

        Screening screening = this.screeningRepository
                .findById(screeningId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No screening with that id found."));

        ticket.setCustomer(customer);
        ticket.setScreening(screening);

        Ticket newTicket = this.ticketRepository.save(ticket);

        customer.getTickets().add(newTicket);

        screening.getTickets().add(newTicket);

        return new ResponseEntity<>(newTicket, HttpStatus.CREATED);
    }

    @GetMapping("/{customerId}/screenings/{screeningId}")
    public ResponseEntity<List<Ticket>> getAllTickets(@PathVariable int customerId, @PathVariable int screeningId) {
        //Get a list of every ticket a customer has booked for a screening.
        List<Ticket> allTicketsACustomerHasBookedForAScreening = new ArrayList<>();

        Customer customer = findCustomerOrThrowException(customerId);

        List<Ticket> customersTickets = customer.getTickets();

        for(Ticket ticket : customersTickets) {
            if(ticket.getScreening().getId() == screeningId) {
                allTicketsACustomerHasBookedForAScreening.add(ticket);
            }
        }

        return ResponseEntity.ok(allTicketsACustomerHasBookedForAScreening);
    }

    private void validateCustomerOrThrowException(Customer customer) {
        if(customer.getName() == null || customer.getEmail() == null || customer.getPhone() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create a new customer, please check all fields are correct.");
        }
    }

    private Customer findCustomerOrThrowException(int id) {
        return this.customerRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No customer with that id found."));
    }
}
