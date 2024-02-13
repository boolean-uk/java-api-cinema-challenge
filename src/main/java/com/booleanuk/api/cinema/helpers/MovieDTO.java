package com.booleanuk.api.cinema.helpers;

import java.time.LocalDateTime;

public record MovieDTO(
        int id,
        String title,
        String rating,
        String description,
        int runtimeMins,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
