package com.booleanuk.api.cinema.model.dto;

import java.time.LocalDateTime;

public record ScreeningDto(
        long id,
        int screenNumber,
        int capacity,
        LocalDateTime startsAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
