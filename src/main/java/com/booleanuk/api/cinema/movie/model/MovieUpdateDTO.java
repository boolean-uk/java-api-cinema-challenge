package com.booleanuk.api.cinema.movie.model;

import com.booleanuk.api.cinema.screening.model.Screening;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@Data

public class MovieUpdateDTO {

    private String title;

    private String rating;

    private String description;

    private Integer runtimeMins;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    private List<Screening> screenings;
}
