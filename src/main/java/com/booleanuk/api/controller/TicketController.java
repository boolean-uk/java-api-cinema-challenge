package com.booleanuk.api.controller;

import com.booleanuk.api.model.Customer;
import com.booleanuk.api.model.Screening;
import com.booleanuk.api.model.Ticket;
import com.booleanuk.api.repository.CustomerRepository;
import com.booleanuk.api.repository.ScreeningRepository;
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
import java.util.List;

@RestController
@RequestMapping("customers")
public class TicketController {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ScreeningRepository screeningRepository;

    @GetMapping("/tickets")
    public ResponseEntity<ResponseTemplate> getAllTickets() {
        List<Ticket> allSpecifiedTickets = this.ticketRepository.findAll();
        if (allSpecifiedTickets.isEmpty()) {
            return new ResponseEntity<>(new NotFoundResponse(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new SuccessResponse(allSpecifiedTickets), HttpStatus.OK);
    }

    @GetMapping("/{customer_id}/screenings/{screening_id}")
    public ResponseEntity<ResponseTemplate> getAllSpecifiedTickets(@PathVariable int customer_id,
                                                                   @PathVariable int screening_id) {
        if (this.doesCustomerIDNotExist(customer_id) ||
                this.doesScreeningIDNotExist(screening_id)) {
            return new ResponseEntity<>(new NotFoundResponse(), HttpStatus.NOT_FOUND);
        }
        List<Ticket> allSpecifiedTickets = new ArrayList<>();
        for (Ticket ticket : this.ticketRepository.findAll()) {
            if (ticket.getCustomer().getId() == customer_id &&
                    ticket.getScreening().getId() == screening_id) {
                allSpecifiedTickets.add(ticket);
            }
        }
        if (allSpecifiedTickets.isEmpty()) {
            return new ResponseEntity<>(new NotFoundResponse(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new SuccessResponse(allSpecifiedTickets), HttpStatus.OK);
    }

    @PostMapping("/{customer_id}/screenings/{screening_id}")
    public ResponseEntity<ResponseTemplate> createTicket(@PathVariable int customer_id,
                                               @PathVariable int screening_id,
                                               @RequestBody Ticket ticket) {
        if (this.doesCustomerIDNotExist(customer_id) ||
                this.doesScreeningIDNotExist(screening_id)) {
            return new ResponseEntity<>(new NotFoundResponse(), HttpStatus.NOT_FOUND);
        }
        if (areAnyFieldsBadForCreating(ticket)) {
            return new ResponseEntity<>(new BadRequestResponse(), HttpStatus.BAD_REQUEST);
        }
        Customer tempCustomer = getCustomerByID(customer_id);
        Screening tempScreening = getScreeningByID(screening_id);
        ticket.setCustomer(tempCustomer);
        ticket.setScreening(tempScreening);
        this.ticketRepository.save(ticket);
        return new ResponseEntity<>(new SuccessResponse(ticket), HttpStatus.CREATED);
    }

    //--------------------------- Private section---------------------------//

    private boolean areAnyFieldsBadForCreating(Ticket ticket) {
        if (ticket.getNumSeats() == 0)
        {
            return true;
        }
        return false;
    }

    private boolean doesCustomerIDNotExist(int id) {
        for (Customer customer : this.customerRepository.findAll()) {
            if (customer.getId() == id) {
                return false;
            }
        }
        return true;
    }

    private boolean doesScreeningIDNotExist(int id) {
        for (Screening screening : this.screeningRepository.findAll()) {
            if (screening.getId() == id) {
                return false;
            }
        }
        return true;
    }

    private Customer getCustomerByID(int id) {
        for (Customer customer : this.customerRepository.findAll()) {
            if (customer.getId() == id) {
                return customer;
            }
        }
        return new Customer();
    }

    private Screening getScreeningByID(int id) {
        for (Screening screening : this.screeningRepository.findAll()) {
            if (screening.getId() == id) {
                return screening;
            }
        }
        return new Screening();
    }
}
