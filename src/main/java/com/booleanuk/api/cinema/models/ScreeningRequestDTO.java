package com.booleanuk.api.cinema.models;

import jakarta.persistence.Column;

public record ScreeningRequestDTO(
        int screenNumber,
        int capacity,
        String startsAt,
        int movie_id
) {}
