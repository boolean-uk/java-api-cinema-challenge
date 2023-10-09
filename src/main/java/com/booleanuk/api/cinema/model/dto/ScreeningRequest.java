package com.booleanuk.api.cinema.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.aspectj.lang.annotation.RequiredTypes;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record ScreeningRequest(
        @NotNull (message = "Should not be null!")
        @NotBlank (message = "Should not be empty!")
        @Min(1)
        Integer screenNumber,
        @NotNull (message = "Should not be null!")
        @NotBlank (message = "Should not be empty!")
        @Min(1)
        Integer capacity,
        @NotNull (message = "Should not be null!")
        @NotBlank (message = "Should not be empty!")
        String startsAt
) {
}
