package com.booleanuk.api.cinema.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "screenings")
@Data
@NoArgsConstructor
public class Screening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "screen_number")
    private int screenNumber;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "starts_at")
    private LocalDateTime startsAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Screening(int id, int screenNumber, int capacity, LocalDateTime startsAt, Movie movie) {
        this.id = id;
        this.screenNumber = screenNumber;
        this.capacity = capacity;
        this.startsAt = startsAt;
        this.movie = movie;
    }

    public Screening(int id, int screenNumber, int capacity, LocalDateTime startsAt, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.screenNumber = screenNumber;
        this.capacity = capacity;
        this.startsAt = startsAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
