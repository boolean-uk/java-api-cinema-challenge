package com.booleanuk.api.cinema.response;

import com.booleanuk.api.cinema.ticket.Ticket;

public class TicketResponse extends Response<Ticket> {
    public TicketResponse (Ticket ticket) {
        super("success", ticket);
    }
}
