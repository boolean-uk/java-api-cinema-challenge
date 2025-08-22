package com.booleanuk.api.cinema.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@NoArgsConstructor
@Entity
@Data
@Table(name="movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String title;

    @NotBlank
    private String rating;

    @NotBlank
    private String description;

    @NotNull
    private Integer runtimeMins;

    private OffsetDateTime created;

    private OffsetDateTime updatedAt;

    @OneToMany(mappedBy = "movie")
    @JsonIgnoreProperties({"movie"})
    List<Screening> screenings;

    public Movie(String title, String rating, String description, int runtimeMins) {
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.runtimeMins = runtimeMins;
    }
}
