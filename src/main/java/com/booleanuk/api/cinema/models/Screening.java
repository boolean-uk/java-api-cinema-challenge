package com.booleanuk.api.cinema.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Screenings")
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int movieId;

    @Column
    private int screenNumber;

    @Column
    private int capacity;

    @Column
    private LocalDateTime startsAt;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "move_id", nullable = false)
    @JsonIncludeProperties(value = {"title", "rating", "description", "runtimeMins"})
    private Movie movie;

    public Screening(int screenNumber, int capacity, LocalDateTime startsAt) {
        //this.movieId = movieId;
        this.screenNumber = screenNumber;
        this.capacity = capacity;
        this.startsAt = startsAt.truncatedTo(ChronoUnit.SECONDS);
    }

    public Screening(int id){
        this.id = id;
    }
}
