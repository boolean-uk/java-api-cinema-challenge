package com.booleanuk.api.cinema.model.dto;

import java.util.List;

public record ScreeningsResponse(
        String status,
        List<ScreeningData> data
) {
}
