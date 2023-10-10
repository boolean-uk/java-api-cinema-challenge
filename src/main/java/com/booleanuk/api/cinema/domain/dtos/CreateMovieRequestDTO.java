package com.booleanuk.api.cinema.domain.dtos;

import com.booleanuk.api.cinema.domain.entities.Screening;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreateMovieRequestDTO {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Rating is required")
    private String rating;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Runtime is required")
    @Min(value = 1, message = "Runtime must be a positive integer")
    private Integer runtimeMins;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<Screening> screenings;

}