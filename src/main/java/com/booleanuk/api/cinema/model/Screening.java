package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "screenings")
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    @JsonIgnore
    // Don't want the movie_id to be shown in the response.
    private Movie movie;

    @Column
    private int screenNumber;

    @Column
    private int capacity;

    @Column
    private ZonedDateTime startsAt;

    @Column
    private ZonedDateTime createdAt;

    @Column
    private ZonedDateTime updatedAt;

    public Screening(int screenNumber, int capacity,  String startsAt) {
        this.screenNumber = screenNumber;
        this.capacity = capacity;
        this.setStartsAt(startsAt);
    }

    public void setStartsAt(String startsAt) {
        // Matches the expected request date format (no 'T' or nanoseconds), but differs from the expected response date format in the spec.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssXXX");
        this.startsAt = ZonedDateTime.parse(startsAt, formatter);
    }

}
