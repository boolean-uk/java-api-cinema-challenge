package com.booleanuk.api.cinema.payload.response;

import com.booleanuk.api.cinema.model.Ticket;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TicketResponse extends Response<Ticket> {
    public TicketResponse(Ticket data) {
        super(data);
    }
}
