package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "movies")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String rating;
    private String description;
    private Integer runtimeMins;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssXXX")
    private OffsetDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssXXX")
    private OffsetDateTime updatedAt;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Screening> screenings;

    public Movie(String title, String rating, String description, Integer runtimeMins) {
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.runtimeMins = runtimeMins;
        createdAt = OffsetDateTime.now();
        updatedAt = OffsetDateTime.now();
        screenings = new ArrayList<>();
    }

    public Movie(String title, String rating, String description, Integer runtimeMins, List<Screening> screenings) {
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.runtimeMins = runtimeMins;
        createdAt = OffsetDateTime.now();
        updatedAt = OffsetDateTime.now();
        this.screenings = new ArrayList<>(screenings);
    }

    public Movie(int id) {
        this.id = id;
        screenings = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(id, movie.id)
                && Objects.equals(title, movie.title)
                && Objects.equals(rating, movie.rating)
                && Objects.equals(description, movie.description)
                && Objects.equals(runtimeMins, movie.runtimeMins)
                && Objects.equals(createdAt, movie.createdAt)
                && Objects.equals(updatedAt, movie.updatedAt)
                && Objects.equals(screenings, movie.screenings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, rating, description, runtimeMins, createdAt, updatedAt, screenings);
    }
}
