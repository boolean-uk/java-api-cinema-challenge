package com.booleanuk.api.cinema.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record MovieScreensRequest(
        @NotNull(message = "Should not be null!")
        @NotBlank(message = "Should not be empty!")
        String title,
        @NotNull(message = "Should not be null!")
        @NotBlank(message = "Should not be empty!")
        String rating,
        @NotNull(message = "Should not be null!")
        @NotBlank(message = "Should not be empty!")
        String description,
        @NotNull(message = "Should not be null!")
        @NotBlank(message = "Should not be empty!")
        @Min(1)
        Integer runtimeMins,
        List<@Valid ScreeningRequest> screenings
) {
}
