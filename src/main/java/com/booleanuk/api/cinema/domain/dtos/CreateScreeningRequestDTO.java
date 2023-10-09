package com.booleanuk.api.cinema.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class CreateScreeningRequestDTO {

    @NotNull(message = "Screen number is required")
    private Integer screenNumber;

    @NotNull(message = "Capacity is required")
    private Integer capacity;

    @NotNull(message = "Start time is required")
    private LocalDateTime startsAt;

    @NotNull(message = "Movie id is required")
    private Long movieId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}