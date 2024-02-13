package com.booleanuk.api.cinema.customer;

import com.booleanuk.api.cinema.responses.ErrorResponse;
import com.booleanuk.api.cinema.responses.Response;
import com.booleanuk.api.cinema.ticket.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("customers")
public class CustomerController {
    @Autowired
    private CustomerRepository repo;

    @PostMapping
    public ResponseEntity<Response<?>> create(@RequestBody Customer customer){
        if (customer.getName() == null ||
                customer.getEmail() == null ||
                customer.getPhone() == null){
            ErrorResponse badRequest = new ErrorResponse();
            badRequest.set("bad request");

            return new ResponseEntity<>(badRequest, HttpStatus.BAD_REQUEST);
        }

        try {
            Integer.parseInt(customer.getPhone());
        } catch (NumberFormatException e){
            ErrorResponse badRequest = new ErrorResponse();
            badRequest.set("bad request");

            return new ResponseEntity<>(badRequest, HttpStatus.BAD_REQUEST);
        }

        customer.setTickets(new ArrayList<Ticket>());
        customer.setCreatedAt(nowFormatted());
        customer.setUpdatedAt(nowFormatted());
        repo.save(customer);

        Response<Customer> customerResponse = new Response<>();
        customerResponse.set(customer);

        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Response<List<Customer>>> getAll(){
        Response<List<Customer>> customerListResponse = new Response<>();
        customerListResponse.set(repo.findAll());

        return ResponseEntity.ok(customerListResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<?>> getOne(@PathVariable int id){
        Customer customer = getById(id);

        if (customer == null){
            ErrorResponse notFound = new ErrorResponse();
            notFound.set("not found");

            return new ResponseEntity<>(notFound, HttpStatus.NOT_FOUND);
        }

        Response<Customer> customerResponse = new Response<>();
        customerResponse.set(customer);

        return ResponseEntity.ok(customerResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<?>> update(@PathVariable int id, @RequestBody Customer customer){
        if (customer.getPhone() != null){
            try {
                Integer.parseInt(customer.getPhone());
            } catch (NumberFormatException e){
                ErrorResponse badRequest = new ErrorResponse();
                badRequest.set("bad request");

                return new ResponseEntity<>(badRequest, HttpStatus.BAD_REQUEST);
            }
        }

        Customer toUpdate = getById(id);

        if (toUpdate == null){
            ErrorResponse notFound = new ErrorResponse();
            notFound.set("not found");

            return new ResponseEntity<>(notFound, HttpStatus.NOT_FOUND);
        }

        Optional.ofNullable(customer.getName())
                .ifPresent(name -> toUpdate.setName(name));
        Optional.ofNullable(customer.getEmail())
                .ifPresent(email -> toUpdate.setEmail(email));
        Optional.ofNullable(customer.getPhone())
                .ifPresent(phone -> toUpdate.setPhone(phone));

        toUpdate.setUpdatedAt(nowFormatted());
        repo.save(toUpdate);

        Response<Customer> customerResponse = new Response<>();
        customerResponse.set(toUpdate);

        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> delete(@PathVariable int id){
        Customer toDelete = getById(id);

        if (toDelete == null){
            ErrorResponse notFound = new ErrorResponse();
            notFound.set("not found");

            return new ResponseEntity<>(notFound, HttpStatus.NOT_FOUND);
        }

        repo.delete(toDelete);
        toDelete.setTickets(new ArrayList<Ticket>());
        Response<Customer> customerResponse = new Response<>();
        customerResponse.set(toDelete);

        return ResponseEntity.ok(customerResponse);
    }

    private Customer getById(int id){
        return repo.findById(id).orElse(null);
    }

    private String nowFormatted(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return now.format(format);
    }
}
