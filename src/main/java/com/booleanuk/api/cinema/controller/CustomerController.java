package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import com.booleanuk.api.cinema.utility.DataResponse;
import com.booleanuk.api.cinema.utility.ErrorResponse;
import com.booleanuk.api.cinema.utility.responses.CustomerListResponse;
import com.booleanuk.api.cinema.utility.responses.CustomerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TicketRepository ticketRepository;

    private Customer getCustomer(int id){
        return this.customerRepository.findById(id).orElse(null);
    }
    @GetMapping
    public ResponseEntity<CustomerListResponse> getAll(){
        CustomerListResponse clr = new CustomerListResponse();
        clr.set(this.customerRepository.findAll(Sort.by(Sort.Direction.ASC,"customerId")));
        return ResponseEntity.ok(clr);
    }

    @PostMapping
    public ResponseEntity<DataResponse<?>> create(@RequestBody Customer customer){
        Customer createdCustomer;

        try {
            customer.setCreatedAt(ZonedDateTime.now());
            customer.setUpdatedAt(ZonedDateTime.now());
            createdCustomer = this.customerRepository.save(customer);
        }catch (Exception e){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Cant create customer check fields");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.set(createdCustomer);
        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataResponse<?>> update(@PathVariable int id, @RequestBody Customer customer){
        Customer update = this.getCustomer(id);
        if(update == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Didnt find customer with that ID");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        try {
            update.setName(customer.getName());
            update.setEmail(customer.getEmail());
            update.setPhone(customer.getPhone());
            update.setUpdatedAt(ZonedDateTime.now());
            update = this.customerRepository.save(update);
        }catch (Exception e){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Cant update customer");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        CustomerResponse response = new CustomerResponse();
        response.set(update);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DataResponse<?>> delete(@PathVariable int id){
        Customer delete = this.getCustomer(id);
        if (delete == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No customer found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        List<Ticket> deleteTickets = this.ticketRepository.findByCustomer(delete);
        if(deleteTickets != null){
            this.ticketRepository.deleteAll(deleteTickets);
        }
        this.customerRepository.delete(delete);
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.set(delete);
        return ResponseEntity.ok(customerResponse);
    }
}
