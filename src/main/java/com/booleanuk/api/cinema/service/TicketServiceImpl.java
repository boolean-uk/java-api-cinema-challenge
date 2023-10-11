package com.booleanuk.api.cinema.service;

import com.booleanuk.api.cinema.dto.TicketDTO;
import com.booleanuk.api.cinema.exceptions.EntityNotFoundException;
import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TicketServiceImpl implements ITicketService {

    private final TicketRepository ticketRepository;
    private final CustomerRepository customerRepository;
    private final ScreeningRepository screeningRepository;
    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, CustomerRepository customerRepository, ScreeningRepository screeningRepository) {
        this.ticketRepository = ticketRepository;
        this.customerRepository = customerRepository;
        this.screeningRepository = screeningRepository;
    }

    @Override
    public List<Ticket> getTickets(int customerId, int screeningId) throws EntityNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() ->
                new EntityNotFoundException(Customer.class, customerId));

        Screening screening = screeningRepository.findById(screeningId).orElseThrow(() ->
                new EntityNotFoundException(Screening.class, screeningId));

        return ticketRepository.findByCustomerIdAndScreeningId(customer.getId(), screening.getId());
    }

    @Override
    public Ticket insertTicket(int customerId, int screeningId, TicketDTO ticketDTO) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() ->
                new EntityNotFoundException(Customer.class, customerId));

        Screening screening = screeningRepository.findById(screeningId).orElseThrow(() ->
                new EntityNotFoundException(Screening.class, screeningId));

        Ticket ticket = new Ticket();
        ticket.setNumSeats(ticketDTO.getNumSeats());
        ticket.setCustomer(customer);
        ticket.setScreening(screening);
        return ticketRepository.save(ticket);
    }
}
