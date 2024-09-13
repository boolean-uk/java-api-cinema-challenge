package com.booleanuk.api.cinema.movie.model;

import com.booleanuk.api.cinema.screening.model.Screening;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

public class MovieUpdateDTO {

    public MovieUpdateDTO(String title, String rating, String description, Integer runtimeMins, List<Screening> screenings) {
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.runtimeMins = runtimeMins;
        this.screenings = screenings;
    }

    private String title;

    private String rating;

    private String description;

    private Integer runtimeMins;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    private List<Screening> screenings;
}
