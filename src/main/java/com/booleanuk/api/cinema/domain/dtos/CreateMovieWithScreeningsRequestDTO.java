package com.booleanuk.api.cinema.domain.dtos;

import jakarta.validation.Valid;
import lombok.Data;

import java.util.List;
@Data
public class CreateMovieWithScreeningsRequestDTO {
    @Valid
    private CreateMovieRequestDTO movie;

    private List<CreateScreeningRequestDTO> screenings;
}