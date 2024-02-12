package com.booleanuk.api.cinema.extension.model;

import java.time.OffsetDateTime;

public record TicketDTO(
        Long id,
        Integer numSeats,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
