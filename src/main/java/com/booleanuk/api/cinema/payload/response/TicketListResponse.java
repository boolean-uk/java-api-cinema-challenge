package com.booleanuk.api.cinema.payload.response;

import com.booleanuk.api.cinema.model.Ticket;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class TicketListResponse extends Response<List<Ticket>> {
    public TicketListResponse(List<Ticket> data) {
        super(data);
    }
}
