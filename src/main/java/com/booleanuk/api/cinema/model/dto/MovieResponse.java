package com.booleanuk.api.cinema.model.dto;

public record MovieResponse(
        String status,
        MovieData data
) {
}
