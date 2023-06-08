package com.booleanuk.api.cinema.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MovieRequest(
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
        Integer runtimeMins
) {
}
