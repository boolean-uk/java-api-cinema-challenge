package com.booleanuk.api.cinema.screening.model;

import com.booleanuk.api.cinema.movie.model.Movie;
import com.booleanuk.api.cinema.ticket.model.Ticket;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

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

    @ManyToOne
    @JsonIncludeProperties(value = {})
    @JoinColumn(name = "movieId")
    private Movie movie;

    @Column(name = "screenNumber", nullable = false)
    private int screenNumber;

    @Column(name = "startsAt", nullable = false)
    private OffsetDateTime startsAt;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updatedAt", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("screenings")
    private List<Ticket> screeningTickets;

    @PrePersist
    private void onCreate() {
        /*
        This method is called before the entity manager saves the entity to the database.
         */
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
