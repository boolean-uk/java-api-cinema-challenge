package com.booleanuk.api.cinema.customer;

import com.booleanuk.api.cinema.ticket.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController {
    @Autowired
    private CustomerRepository repo;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody Customer customer){
        customer.setTickets(new ArrayList<Ticket>());
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());

        return new ResponseEntity<Customer>(repo.save(customer), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Customer> getAll(){
        return repo.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Customer> getOne(@PathVariable int id){
        return ResponseEntity.ok(getById(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<Customer> update(@PathVariable int id, @RequestBody Customer customer){
        Customer toUpdate = getById(id);

        toUpdate.setName(customer.getName());
        toUpdate.setEmail(customer.getEmail());
        toUpdate.setPhone(customer.getPhone());
        toUpdate.setUpdatedAt(LocalDateTime.now());
        //TODO set tickets?

        return new ResponseEntity<Customer>(repo.save(toUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Customer> delete(@PathVariable int id){
        Customer toDelete = getById(id);
        repo.delete(toDelete);
        toDelete.setTickets(new ArrayList<Ticket>());

        return ResponseEntity.ok(toDelete);
    }

    private Customer getById(int id){
        return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
