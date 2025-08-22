package com.booleanuk.api.cinema.models;

public record TicketRequestDTO(
        int numSeats,
        int customer_id,
        int screening_id
) {}
