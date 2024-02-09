package com.booleanuk.api.cinema.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TicketDto {
    private int id;
    private int numSeats;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public TicketDto(int id, int numSeats, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.numSeats = numSeats;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
