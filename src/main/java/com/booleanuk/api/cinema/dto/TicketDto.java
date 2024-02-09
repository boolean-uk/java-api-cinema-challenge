package com.booleanuk.api.cinema.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TicketDto {
    private int id;
    private int numSeats;
    private Date createdAt;
    private Date updatedAt;

    public TicketDto(int id, int numSeats, Date createdAt, Date updatedAt) {
        this.id = id;
        this.numSeats = numSeats;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
