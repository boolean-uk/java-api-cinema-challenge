package com.booleanuk.api.cinema.screenings;

import com.booleanuk.api.cinema.movies.Movie;
import com.booleanuk.api.cinema.tickets.Ticket;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "screenings")
@JsonIgnoreProperties({"tickets"})
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private int screenNumber;
    @Column
    private LocalDateTime startsAt;
    @Column
    private int capacity;
    @Column
    private LocalDateTime createdAt;
    @Column
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    @JsonIncludeProperties({"title", "rating", "description", "runtime_mins"})
    private Movie movie;

    @OneToMany(mappedBy = "screening", cascade = CascadeType.REMOVE)
    @JsonIncludeProperties({"customer_id", "screening_id", "num_seats"})
    private List<Ticket> tickets;

    public Screening(Movie movie, int screenNumber, LocalDateTime startsAt, int capacity) {
        this.movie = movie;
        this.screenNumber = screenNumber;
        this.startsAt = startsAt;
        this.capacity = capacity;
    }

    public Screening(int screenNumber, LocalDateTime startsAt, int capacity) {
        this.screenNumber = screenNumber;
        this.startsAt = startsAt;
        this.capacity = capacity;
    }

    public Screening(int id) {
        this.id = id;
    }
}