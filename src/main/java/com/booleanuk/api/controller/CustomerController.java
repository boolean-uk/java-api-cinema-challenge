package com.booleanuk.api.controller;

import com.booleanuk.api.model.Customer;
import com.booleanuk.api.model.Ticket;
import com.booleanuk.api.repository.CustomerRepository;
import com.booleanuk.api.repository.TicketRepository;
import com.booleanuk.api.response.BadRequestResponse;
import com.booleanuk.api.response.NotFoundResponse;
import com.booleanuk.api.response.ResponseTemplate;
import com.booleanuk.api.response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("customers")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping
    public ResponseEntity<ResponseTemplate> getAllCustomers() {
        return new ResponseEntity<>(new SuccessResponse(this.customerRepository.findAll()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseTemplate> createCustomer(@RequestBody Customer customer) {
        if (areAnyFieldsBad(customer)) {
            return new ResponseEntity<>(new BadRequestResponse(), HttpStatus.BAD_REQUEST);
        }
        customer.setTickets(new ArrayList<>());
        this.customerRepository.save(customer);
        return new ResponseEntity<>(new SuccessResponse(customer), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseTemplate> deleteCustomer(@PathVariable int id) {
        if (doesCustomerIDNotExist(id)) {
            return new ResponseEntity<>(new NotFoundResponse(), HttpStatus.NOT_FOUND);
        }
        Customer customerToDelete = this.getCustomerByID(id);
        if (!customerToDelete.getTickets().isEmpty()) {
            removeRelatedTickets(customerToDelete);
        }
        this.customerRepository.delete(customerToDelete);
        customerToDelete.setTickets(new ArrayList<>());
        return new ResponseEntity<>(new SuccessResponse(customerToDelete), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseTemplate> updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        if (customer == null || (customer.getName() == null && customer.getEmail() == null
                && customer.getPhone() == null)){
            return new ResponseEntity<>(new BadRequestResponse(), HttpStatus.BAD_REQUEST);
        }
        if (doesCustomerIDNotExist(id)) {
            return new ResponseEntity<>(new NotFoundResponse(), HttpStatus.NOT_FOUND);
        }
        Customer customerToUpdate = getCustomerByID(id);
        if (customer.getName() != null && !customer.getName().isBlank()) {
            customerToUpdate.setName(customer.getName());
        }
        if (customer.getEmail() != null && !customer.getEmail().isBlank()) {
            customerToUpdate.setEmail(customer.getEmail());
        }
        if (customer.getPhone() != null && !customer.getPhone().isBlank()) {
            customerToUpdate.setPhone(customer.getPhone());
        }
        this.customerRepository.save(customerToUpdate);
        return new ResponseEntity<>(new SuccessResponse(customerToUpdate), HttpStatus.CREATED);
    }

    //--------------------------- Private section---------------------------//

    private void removeRelatedTickets(Customer customer) {
        for (Ticket ticket : customer.getTickets()) {
            this.ticketRepository.delete(ticket);
        }
    }

    private Customer getCustomerByID(int id) {
        for (Customer customer : this.customerRepository.findAll()) {
            if (customer.getId() == id) {
                return customer;
            }
        }
        return new Customer();
    }

    private boolean doesCustomerIDNotExist(int id) {
        for (Customer customer : this.customerRepository.findAll()) {
            if (customer.getId() == id) {
                return false;
            }
        }
        return true;
    }

    private boolean areAnyFieldsBad(Customer customer) {
        if (customer.getName() == null ||
            customer.getEmail() == null ||
            customer.getPhone() == null ||
            customer.getName().isBlank() ||
            customer.getEmail().isBlank() ||
            customer.getPhone().isBlank())
        {
            return true;
        }
        return false;
    }
}
