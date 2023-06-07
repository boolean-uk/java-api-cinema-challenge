package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.entities.Customer;
import com.booleanuk.api.cinema.entities.Ticket;
import com.booleanuk.api.cinema.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


record singleCustomerResponse(String status,Customer data){}
record singleTicketResponse(String status, Ticket data){}
record multiCustomerResponse(String status, List<Customer> data){}
record multiTicketResponse(String status, List<Ticket> data){}

@RestController
@RequestMapping("customers")
public class CustomerController {
    @Autowired
    CustomerService customerService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public singleCustomerResponse createCustomer(@RequestBody Customer requestCustomer) {
        return new singleCustomerResponse("success", customerService.createCustomer(requestCustomer));
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public multiCustomerResponse getAllCustomers(){
        return new multiCustomerResponse("succeess",customerService.getAllCustomers());
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public singleCustomerResponse updateCustomer(@PathVariable Integer id,@RequestBody Customer requestCustomer){
        return new singleCustomerResponse("success", customerService.updateCustomer(id,requestCustomer));
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public singleCustomerResponse deleteCustomer(@PathVariable Integer id){
        return new singleCustomerResponse("success", customerService.deleteCustomer(id));
    }
    @PostMapping("/{customerId}/screenings/{screeningId}")
    @ResponseStatus(HttpStatus.CREATED)
    public singleTicketResponse bookATicket(@PathVariable Integer customerId,@PathVariable Integer screeningId,@RequestBody Ticket ticket){
        return new singleTicketResponse("success",customerService.bookATicket(customerId,screeningId,ticket));
    }
    @GetMapping("/{customerId}/screenings/{screeningId}")
    @ResponseStatus(HttpStatus.OK)
    public multiTicketResponse getAllTickets(@PathVariable Integer customerId,@PathVariable Integer screeningId){
        return new multiTicketResponse("success",customerService.getAllTickets(customerId,screeningId));
    }
}
