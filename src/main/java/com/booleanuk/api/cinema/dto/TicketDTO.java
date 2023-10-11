package com.booleanuk.api.cinema.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TicketDTO {

    private int id;

    @NotNull
    private int numSeats;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public TicketDTO(int id, int numSeats, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.numSeats = numSeats;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}

