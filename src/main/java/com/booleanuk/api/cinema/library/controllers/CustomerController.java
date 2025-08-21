package com.booleanuk.api.cinema.library.controllers;

import com.booleanuk.api.cinema.library.models.Customer;
import com.booleanuk.api.cinema.library.models.Screening;
import com.booleanuk.api.cinema.library.models.Ticket;
import com.booleanuk.api.cinema.library.repository.CustomerRepository;
import com.booleanuk.api.cinema.library.repository.ScreeningRepository;
import com.booleanuk.api.cinema.library.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Customer> getCustomerById(@PathVariable int id) {
        return customerRepository.findById(id);
    }

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable int id, @RequestBody Customer updatedCustomer) {
        return customerRepository.findById(id).map(customer -> {
            customer.setName(updatedCustomer.getName());
            customer.setEmail(updatedCustomer.getEmail());
            customer.setPhone(updatedCustomer.getPhone());
            return customerRepository.save(customer);
        }).orElseGet(() -> {
            updatedCustomer.setId(id);
            return customerRepository.save(updatedCustomer);
        });
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable int id) {
        customerRepository.deleteById(id);
    }

    @PostMapping("/{customerId}/tickets")
    public Ticket createTicket(@PathVariable int customerId,
                               @RequestParam int screeningId,
                               @RequestParam int numSeats) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        Screening screening = screeningRepository.findById(screeningId)
                .orElseThrow(() -> new RuntimeException("Screening not found"));

        Ticket ticket = new Ticket();
        ticket.setCustomer(customer);
        ticket.setScreening(screening);
        ticket.setNumSeats(numSeats);

        return ticketRepository.save(ticket);
    }
}
