package com.booleanuk.api.cinema.service;

import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class TicketServiceImp implements TicketService{
    @Autowired
    private TicketRepository ticketRepository;
    @Override
    public List<Ticket> getTickets() {
        return ticketRepository.findAll();
    }

    @Override
    public Ticket getTicket(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Not found"));
    }

    @Override
    public Ticket createTicket(Ticket Ticket) {
        LocalDateTime createdAt = LocalDateTime.now();
        Ticket.setCreatedAt(createdAt);
        Ticket.setUpdatedAt(createdAt);
        return ticketRepository.save(Ticket);
    }

    @Override
    public Ticket updateTicket(Long id, Ticket Ticket) {
        Ticket foundTicket = ticketRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"No Ticket matches the provided id"));

        Ticket.setId(id);
        Ticket.setCreatedAt(foundTicket.getCreatedAt());
        Ticket.setUpdatedAt(LocalDateTime.now());
        return ticketRepository.save(Ticket);
    }

    @Override
    public Ticket deleteTicket(Long id) {
        Ticket toBeDeleted = ticketRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"No Ticket matches the provided id"));

        ticketRepository.delete(toBeDeleted);
        return toBeDeleted;
    }
}
