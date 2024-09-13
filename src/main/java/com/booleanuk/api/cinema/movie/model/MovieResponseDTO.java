package com.booleanuk.api.cinema.movie.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor

public class MovieResponseDTO {

    public MovieResponseDTO(int id, String title, String rating, String description, Integer runtimeMins, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.runtimeMins = runtimeMins;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    private int id;

    private String title;

    private String rating;

    private String description;

    private Integer runtimeMins;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;
}
