package com.booleanuk.api.cinema.model.dto;

public record TicketResponse(
        String status,
        TicketDto data
) {
}
