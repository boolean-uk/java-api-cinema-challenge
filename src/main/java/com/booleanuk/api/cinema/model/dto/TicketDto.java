package com.booleanuk.api.cinema.model.dto;


import java.time.LocalDateTime;

public record TicketDto(
        long id,
        int numSeats,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
