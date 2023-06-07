package com.booleanuk.api.cinema.services.ticket;

import com.booleanuk.api.cinema.entities.Customer;
import com.booleanuk.api.cinema.entities.Screening;
import com.booleanuk.api.cinema.entities.Ticket;
import com.booleanuk.api.cinema.repositories.CustomerRepo;
import com.booleanuk.api.cinema.repositories.ScreeningRepo;
import com.booleanuk.api.cinema.repositories.TicketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
public class ITicketService implements TicketServiceInterface {
    @Autowired
    TicketRepo ticketRepo;
    @Autowired
    CustomerRepo customerRepo;
    @Autowired
    ScreeningRepo screeningRepo;

    @Override
    public List<Ticket> findByCustomerIdAndScreeningId(int customerId, int screeningId) {
//        Customer customer = customerRepo.findById(customerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Customer with that id"));
//        Screening screening = screeningRepo.findById(screeningId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Screening with that id"));
        System.out.println("hello");
        List<Ticket> tickets = ticketRepo.findByCustomerIdAndScreeningId(customerId, screeningId);
        return tickets;
    }

    @Override
    public Ticket createTicket(int customerId, int screeningId, Ticket ticket) {
        Customer customer = customerRepo.findById(customerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Customer with that id"));
        Screening screening = screeningRepo.findById(screeningId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Screening with that id"));
        ticket.setCustomer(customer);
        ticket.setScreening(screening);
        return ticketRepo.save(ticket);
    }
}
