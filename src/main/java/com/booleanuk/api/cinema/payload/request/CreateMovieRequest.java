package com.booleanuk.api.cinema.payload.request;

import com.booleanuk.api.cinema.models.Screening;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateMovieRequest {
    @NotBlank
    String title;

    @NotNull
    String rating;

    @NotNull
    String description;

    @NotNull
    Integer runtimeMins;

    @Valid
    List<Screening> screenings;
}
