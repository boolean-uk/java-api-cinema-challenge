package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "screenings")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer screenNumber;
    private Integer capacity;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssXXX")
    private OffsetDateTime startsAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssXXX")
    private OffsetDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssXXX")
    private OffsetDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "movie", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Movie movie;

    @OneToMany(mappedBy = "screening", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Ticket> tickets;

    public Screening(Integer screenNumber, Integer capacity, OffsetDateTime startsAt, Movie movie) {
        this.screenNumber = screenNumber;
        this.capacity = capacity;
        this.startsAt = startsAt;
        OffsetDateTime now = OffsetDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
        this.movie = movie;
        this.tickets = new ArrayList<>();
    }

    public Screening(int id) {
        this.id = id;
        OffsetDateTime now = OffsetDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
        this.tickets = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Screening screening = (Screening) o;
        return Objects.equals(id, screening.id)
                && Objects.equals(screenNumber, screening.screenNumber)
                && Objects.equals(capacity, screening.capacity)
                && Objects.equals(startsAt, screening.startsAt)
                && Objects.equals(createdAt, screening.createdAt)
                && Objects.equals(updatedAt, screening.updatedAt)
                && Objects.equals(movie, screening.movie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, screenNumber, capacity, startsAt, createdAt, updatedAt, movie);
    }
}
