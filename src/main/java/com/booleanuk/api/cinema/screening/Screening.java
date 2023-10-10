package com.booleanuk.api.cinema.screening;

import com.booleanuk.api.cinema.movie.Movie;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "screenings")
@Getter
@Setter
public class Screening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "screenNumber")
    int screenNumber;

    @Column(name = "capacity")
    int capacity;

    @Column(name = "startsAt")
    String startsAt;

    @Column(name = "createdAt")
    private Date createdAt;

    @Column(name = "updatedAt")
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    @JsonIgnoreProperties({"screenings"})
    private Movie movie;

    public Screening(int id, int screenNumber, int capacity, String startsAt, Date createdAt, Date updatedAt, Movie movie) {
        this.id = id;
        this.screenNumber = screenNumber;
        this.capacity = capacity;
        this.startsAt = startsAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.movie = movie;
    }

    public Screening() {
    }
}
