package com.booleanuk.api.cinema.Dtos.tickets;

import com.booleanuk.api.cinema.entities.Ticket;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TicketResponseSingleDto {
    private String status;
    private Ticket data;
}
