package com.booleanuk.api.cinema.Controller;

import com.booleanuk.api.cinema.Model.Customer;
import com.booleanuk.api.cinema.Model.Movie;
import com.booleanuk.api.cinema.Model.Screening;
import com.booleanuk.api.cinema.Repository.CustomerRepository;
import com.booleanuk.api.cinema.Repository.CustomerRepository;
import com.booleanuk.api.cinema.Response.Error;
import com.booleanuk.api.cinema.Response.Response;
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
    private CustomerRepository repository;


    @GetMapping
    public ResponseEntity<Object> getAll() {
        List<Customer> all = this.repository.findAll();
        return new ResponseEntity<Object>(new Response("success", all), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody Customer customer) {
        if (customer.getName() == null || customer.getEmail() == null || customer.getPhone() == null) {
            return new ResponseEntity<Object>(new Response("error", new Error("bad request")), HttpStatus.BAD_REQUEST);
        }
        customer.setTime();
        customer = this.repository.save(customer);
        return new ResponseEntity<Object>(new Response("success", customer), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable int id, @RequestBody Customer request) {
        if (this.repository.findById(id).isEmpty()) {
            return new ResponseEntity<Object>(new Response("error", new Error("not found")), HttpStatus.NOT_FOUND);
        }
        if (request.getName() == null ||
                request.getEmail() == null ||
                request.getPhone() == null) {
            return new ResponseEntity<Object>(new Response("error", new Error("bad request")), HttpStatus.BAD_REQUEST);
        }
        Customer customer = this.repository.findById(id).get();

        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        customer.updateUpdatedAt();
        customer = this.repository.save(customer);
        return new ResponseEntity<Object>(new Response("success", customer), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id) {
        if (this.repository.findById(id).isEmpty()) {
            return new ResponseEntity<Object>(new Response("error", new Error("not found")), HttpStatus.NOT_FOUND);
        }
        Customer customer = this.repository.findById(id).get();
        this.repository.delete(customer);
        return new ResponseEntity<Object>(new Response("success", customer), HttpStatus.OK);
    }
}
