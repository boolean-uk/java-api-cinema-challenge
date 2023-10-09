package com.booleanuk.api.cinema.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UpdateMovieRequestDTO {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Rating is required")
    private String rating;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Runtime is required")
    @Positive
    private Integer runtimeMins;

}