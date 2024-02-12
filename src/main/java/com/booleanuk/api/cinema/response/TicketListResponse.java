package com.booleanuk.api.cinema.response;
import com.booleanuk.api.cinema.model.Ticket;

import java.util.List;

public class TicketListResponse extends Response<List<Ticket>> {
    private List<Ticket> data;
    public TicketListResponse(List<Ticket> data) {
        super("Success",data);
    }
}