package com.booleanuk.api.cinema.movie.model;

import com.booleanuk.api.cinema.screening.model.ScreeningDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor

public class MovieDTO {
    /*
    Another DTO to handle the messed up time format from the API spec for screenings.
     */
    public MovieDTO (String title, String rating, String description, int runtimeMins, List<ScreeningDTO> screeningsDTO) {
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.runtimeMins = runtimeMins;
        this.screenings = screeningsDTO;
    }

    private int id;

    private String title;

    private String rating;

    private String description;

    private int runtimeMins;

    private List<ScreeningDTO> screenings;
}
