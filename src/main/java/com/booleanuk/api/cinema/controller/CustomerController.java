package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.GenericResponse;
import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.service.CustomerService;
import com.booleanuk.api.cinema.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("customers")
public class CustomerController {

    private final CustomerService customerService;
    private final TicketService ticketService;

    @Autowired
    public CustomerController(CustomerService customerService, TicketService ticketService){
        this.customerService = customerService;
        this.ticketService = ticketService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GenericResponse<Customer> create(@RequestBody Customer customer){
        return customerService.create(customer);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public GenericResponse<List<Customer>> getAll(){
        return customerService.getAll();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public GenericResponse<Customer> update(@PathVariable int id, @RequestBody Customer customer){
        return customerService.update(id, customer);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GenericResponse<Customer> delete(@PathVariable int id) {
        return customerService.delete(id);
    }

    @PostMapping("/{customerId}/screenings/{screeningId}")
    @ResponseStatus(HttpStatus.CREATED)
    public GenericResponse<Ticket> create(
            @PathVariable(name = "customerId") int customerId, @PathVariable(name = "screeningId") int screeningId,
            @RequestBody Ticket ticket) {

        return ticketService.create(customerId, screeningId, ticket);
    }

    @GetMapping("/{customerId}/screenings/{screeningId}")
    @ResponseStatus(HttpStatus.OK)
    public GenericResponse<List<Ticket>> getAllTicketsForCustomerAndScreening(
            @PathVariable(name = "customerId") int customerId,
            @PathVariable(name = "screeningId") int screeningId){

        return ticketService.getAllForCustomerAndScreening(customerId, screeningId);
    }
}
