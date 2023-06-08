package com.booleanuk.api.cinema.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TicketRequest(
        @NotNull(message = "Should not be null!")
        @NotBlank(message = "Should not be empty!")
        @Min(1)
        Integer numSeats
) {
}
