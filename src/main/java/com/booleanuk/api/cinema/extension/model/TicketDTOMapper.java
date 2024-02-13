package com.booleanuk.api.cinema.extension.model;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@AllArgsConstructor
@Service
public class TicketDTOMapper implements Function<Ticket, TicketDTO>{
    @Override
    public TicketDTO apply(Ticket ticket) {
        return new TicketDTO(
                ticket.getId(),
                ticket.getNumSeats(),
                ticket.getCreatedAt(),
                ticket.getUpdatedAt());
    }
}
