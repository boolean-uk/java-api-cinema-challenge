package com.booleanuk.api.cinema.response;
import com.booleanuk.api.cinema.model.Ticket;

public class TicketResponse extends Response<Ticket> {
    private Ticket data;
    public TicketResponse(Ticket ticket) {
        super("Success",ticket);
    }
}