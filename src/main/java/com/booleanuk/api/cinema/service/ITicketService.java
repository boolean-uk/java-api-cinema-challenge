package com.booleanuk.api.cinema.service;

import com.booleanuk.api.cinema.dto.TicketDTO;
import com.booleanuk.api.cinema.model.Ticket;

import java.util.List;

public interface ITicketService {

    List<Ticket> getTickets(int customerId, int screeningId);
    Ticket insertTicket(int customerId, int screeningId, TicketDTO ticketDTO);

}