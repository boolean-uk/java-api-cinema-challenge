package com.booleanuk.api.cinema.screenings;

import com.booleanuk.api.cinema.movies.Movie;
import com.booleanuk.api.cinema.tickets.Ticket;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "screenings")
@JsonIgnoreProperties({"tickets", "movie"})
public class Screening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int screenNumber;

    @Column
    private LocalDate startsAt;

    @Column
    private int capacity;

    @Column
    private LocalDate createdAt;

    @Column
    private LocalDate updatedAt;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "screening", orphanRemoval = true)
    private List<Ticket> tickets;

    @ManyToOne
    @JoinColumn(name="movie")
    private Movie movie;


    public Screening(Movie movie, int screenNumber, LocalDate startsAt, int capacity) {
        this.movie = movie;
        this.screenNumber = screenNumber;
        this.startsAt = startsAt;
        this.capacity = capacity;
        createdAt = LocalDate.now();
        updatedAt = LocalDate.now();

    }

    public Screening(int id) {
        this.id = id;
    }

    public Screening() {
        createdAt = LocalDate.now();
        updatedAt = LocalDate.now();
    }
}
