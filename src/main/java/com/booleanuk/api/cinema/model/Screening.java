package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
public class Screening extends TimestampedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;
    @NotNull
    @JsonProperty(index = 2)
    private long screenNumber;
    @NotNull
    @JsonProperty(index = 3)
    private int capacity;
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty(index = 4)
    private LocalDateTime startsAt;
    @ManyToOne
    @JoinColumn(name = "movie_id")
    @JsonIgnore
    private Movie movie;

    @JsonProperty(value = "id", index = 1)
    public Long getMovieId() {
        return movie.getId();
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
