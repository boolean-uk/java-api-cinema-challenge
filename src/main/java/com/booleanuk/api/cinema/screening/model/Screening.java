package com.booleanuk.api.cinema.screening.model;

import com.booleanuk.api.cinema.movie.model.Movie;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "screenings")
public class Screening {
    public Screening (int screenNumber, int capacity, OffsetDateTime startsAt) {
        this.screenNumber = screenNumber;
        this.capacity = capacity;
        this.startsAt = startsAt;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "screenNumber is required")
    @Column(name = "screenNumber", nullable = false)
    private Integer screenNumber;

    @NotNull(message = "startsAt is required")
    @Column(name = "startsAt", nullable = false)
    private OffsetDateTime startsAt;

    @NotNull(message = "capacity is required")
    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updatedAt", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "movieId", nullable = false)
    @JsonBackReference
    private Movie movie;

    @PrePersist
    private void onCreate() {
        /*
        This method is called before the entity manager saves the entity to the database.
         */
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
