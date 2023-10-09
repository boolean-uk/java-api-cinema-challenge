package com.booleanuk.api.cinema.model.dto;

import java.time.LocalDateTime;

public record MovieDto(
        long id,
        String title,
        String rating,
        String description,
        int runtimeMins,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}
