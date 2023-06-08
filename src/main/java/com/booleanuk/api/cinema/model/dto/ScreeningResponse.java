package com.booleanuk.api.cinema.model.dto;

public record ScreeningResponse(
        String status,
        ScreeningData data
) {
}
