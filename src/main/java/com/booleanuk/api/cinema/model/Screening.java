package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "screenings")
public class Screening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    @JsonBackReference(value = "movie-screenings")
    private Movie movie;

    @Column(name = "screen_number")
    private int screenNumber;

    @Column(name = "starts_at")
    private LocalDateTime startsAt;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Screening(Movie movie, int screenNumber, LocalDateTime startsAt, int capacity) {
        this.movie = movie;
        this.screenNumber = screenNumber;
        this.startsAt = startsAt;
        this.capacity = capacity;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Used to update object.
    public Screening(Movie movie, int screenNumber, LocalDateTime startsAt, int capacity, LocalDateTime time) {
        this.movie = movie;
        this.screenNumber = screenNumber;
        this.startsAt = startsAt;
        this.capacity = capacity;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = time;
    }

    public Screening() {}

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public int getScreenNumber() {
        return screenNumber;
    }

    public void setScreenNumber(int screenNumber) {
        this.screenNumber = screenNumber;
    }

    public LocalDateTime getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(LocalDateTime startsAt) {
        this.startsAt = startsAt;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
