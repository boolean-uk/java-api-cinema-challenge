package com.booleanuk.api.cinema.service;

import com.booleanuk.api.cinema.GenericResponse;
import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;

    private final CustomerService customerService;
    private final ScreeningService screeningService;
    @Autowired
    public TicketService(TicketRepository ticketRepository, CustomerService customerService, ScreeningService screeningService){
        this.ticketRepository = ticketRepository;
        this.customerService = customerService;
        this.screeningService = screeningService;
    }

    public GenericResponse<Ticket> create(int customerId, int screeningId, Ticket ticket){

        Customer customer = customerService.getById(customerId);
        Screening screening = screeningService.getById(screeningId);

        ticket.setCustomer(customer);
        ticket.setScreening(screening);
        try {
            return new GenericResponse<Ticket>()
                    .from(ticketRepository.save(ticket));
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create ticket, please check all required fields are correct");
        }
    }

    public GenericResponse<List<Ticket>> getAllForCustomerAndScreening(int customerId, int screeningId){
        List<Ticket> tickets = ticketRepository.findByCustomerIdAndScreeningId(customerId, screeningId);

        return new GenericResponse<List<Ticket>>()
                .from(tickets);
    }

}
