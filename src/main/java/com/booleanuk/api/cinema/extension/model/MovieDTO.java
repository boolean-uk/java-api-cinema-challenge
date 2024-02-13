package com.booleanuk.api.cinema.extension.model;

import java.time.OffsetDateTime;

public record MovieDTO(
        Long id,
        String title,
        String rating,
        String description,
        Integer runtimeMins,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
