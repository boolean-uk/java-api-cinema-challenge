package com.booleanuk.api.cinema.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="screenings")
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @ManyToOne
    private Movie movie;

    @Column private int screenNumber;
    @Column private int capacity;
    @Column private LocalDateTime startsAt;
    @Column private LocalDateTime createdAt;
    @Column private LocalDateTime updatedAt;

    public Screening(String id, Movie movie, int screenNumber, int capacity, LocalDateTime startsAt, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.movie = movie;
        this.screenNumber = screenNumber;
        this.capacity = capacity;
        this.startsAt = startsAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Screening(Movie movie, int screenNumber, int capacity, LocalDateTime startsAt, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.movie = movie;
        this.screenNumber = screenNumber;
        this.capacity = capacity;
        this.startsAt = startsAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Screening(String id) {
        this.id = id;
    }

    public Screening() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public LocalDateTime getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(LocalDateTime startsAt) {
        this.startsAt = startsAt;
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
