package com.booleanuk.api.cinema.domain.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class CreateScreeningRequestDTO {

    @NotNull(message = "Screen number is required")
    @Min(value = 1, message = "Screen number must be a positive integer")
    private Integer screenNumber;

    @NotNull(message = "Capacity is required")
    @Min(value = 1, message = "Capacity must be a positive integer")
    private Integer capacity;

    @NotNull(message = "Start time is required")
    private LocalDateTime startsAt;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}