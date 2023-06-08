package com.booleanuk.api.cinema.model.dto;

import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

public record TicketsResponse(
        String status,
        List<TicketDto> data
) {
}
