package com.booleanuk.api.cinema.movie.model;

import com.booleanuk.api.cinema.screening.model.Screening;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

public class MovieRequestDTO {

    public MovieRequestDTO(String title, String rating, String description, Integer runtimeMins) {
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.runtimeMins = runtimeMins;
        this.screenings = new ArrayList<>();
    }

    public MovieRequestDTO(String title, String rating, String description, Integer runtimeMins, List<Screening> screenings) {
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.runtimeMins = runtimeMins;
        this.screenings = screenings;
    }

    // Bean validation framework. Validates object fields at application level.
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Rating is required")
    private String rating;

    @NotBlank(message = "Description is required")
    private String description;

    // Validation check for non-string fields.
    @NotNull(message = "RuntimeMins is required")
    private Integer runtimeMins;

    // Optional
    private List<Screening> screenings;
}
