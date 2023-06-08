package com.booleanuk.api.cinema.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ScreeningData(
        long id,
        int screenNumber,
        int capacity,
        LocalDateTime startsAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
