package com.booleanuk.api.cinema.response;

import com.booleanuk.api.cinema.ticket.Ticket;

import java.util.List;

public class TicketListResponse extends Response<List<Ticket>>{
    public TicketListResponse(List<Ticket> data) {
        super ("success", data);
    }
}
