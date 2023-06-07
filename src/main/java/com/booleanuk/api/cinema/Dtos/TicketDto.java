package com.booleanuk.api.cinema.Dtos;

import com.booleanuk.api.cinema.entities.Customer;
import com.booleanuk.api.cinema.entities.Screening;
import com.booleanuk.api.cinema.entities.Ticket;
import lombok.Data;

@Data
public class TicketDto {
    private Integer numSeats;

    public Ticket toTicket(Customer customer, Screening screening) {
        return new Ticket(0, customer, screening, numSeats, null, null);
    }
}
