package com.booleanuk.api.cinema.Dtos.tickets;

import com.booleanuk.api.cinema.entities.Ticket;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TicketResponseDto {
    private String status;
    private List<Ticket> data;
}
