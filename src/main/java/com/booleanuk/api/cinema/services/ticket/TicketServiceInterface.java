package com.booleanuk.api.cinema.services.ticket;

import com.booleanuk.api.cinema.entities.Ticket;

import java.util.List;

public interface TicketServiceInterface {

    List<Ticket> findByCustomerIdAndScreeningId(int customerId, int screeningId);

    Ticket createTicket(int customerId, int screeningId, Ticket ticket);
}
