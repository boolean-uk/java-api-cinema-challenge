package com.booleanuk.api.cinema.service;

import com.booleanuk.api.cinema.model.Ticket;

import java.util.List;

public interface TicketService {
    List<Ticket> getTickets();
    Ticket getTicket(Long id);
    Ticket createTicket(Ticket ticket);
    Ticket updateTicket(Long id, Ticket ticket);
    Ticket deleteTicket(Long id);
}
