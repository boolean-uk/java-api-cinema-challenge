package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public List<Customer> getAll(){
        return this.customerRepository.findAll();
    }
    //not necessary but will come in handy later
    @GetMapping("/{id}")
    public Customer getCustomer(@PathVariable int id){
        return this.customerRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Customer with given id not found"));
    }
    public record CustomerRequest(String name, String email,String phone){}
    public record ScreeningRequest(int numOfSeats){}
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody CustomerRequest customer){
        if(customer.name == null || customer.email == null || customer.phone == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid body for Customer");
        }
        Customer createdCustomer = new Customer(customer.name,customer.email, customer.phone);
        return new ResponseEntity<>(this.customerRepository.save(createdCustomer),HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable int id, @RequestBody CustomerRequest customer){
        Customer updatedCustomer = this.getCustomer(id);
        if(customer.name != null && !customer.name.isEmpty()){
            updatedCustomer.setName(customer.name);
            updatedCustomer.setUpdatedAt();
        }
        if(customer.phone != null && !customer.phone.isEmpty()){
            updatedCustomer.setPhone(customer.phone);
            updatedCustomer.setUpdatedAt();
        }
        if(customer.email != null && !customer.email.isEmpty()){
            updatedCustomer.setEmail(customer.email);
            updatedCustomer.setUpdatedAt();
        }
        return new ResponseEntity<>(updatedCustomer,HttpStatus.OK);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable int id){
        Customer toDelete = getCustomer(id);
        this.customerRepository.delete(toDelete);
        return new ResponseEntity<>(toDelete,HttpStatus.OK);
    }
    //everything under here is about tickets
    @GetMapping("/{customerId}/screenings/{screeningId}")
    public List<Ticket> getTickets(@PathVariable (name = "customerId") int customerId, @PathVariable (name = "screeningId") int screeningId){
        List<Ticket> tickets = this.ticketRepository.findAll().stream().filter((ticket -> ticket.getCustomer().getId() == customerId)).filter(ticket -> ticket.getScreening().getId() == screeningId).toList();
        if(tickets.size() > 0){
            return tickets;
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No tickets found for that customer in that screening of the movie");
        }
    }
    @PostMapping("/{customerId}/screenings/{screeningId}")
    public ResponseEntity<Ticket> createTicket(@PathVariable (name = "customerId") int customerId, @PathVariable (name = "screeningId") int screeningId, @RequestBody ScreeningRequest screeningRequest){
        Customer customer = this.getCustomer(customerId);
        Screening screening = this.screeningRepository.findById(screeningId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Screening with that id not found"));
        if(screeningRequest.numOfSeats == 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"The number of seats is required when booking a ticket");
        }
        Ticket added = new Ticket(screeningRequest.numOfSeats);
        customer.getTickets().add(added);
        screening.getTickets().add(added);
        return new ResponseEntity<>(this.ticketRepository.save(added),HttpStatus.OK);
    }

}
