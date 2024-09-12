package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "screenings")
public class Screening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int screenNumber;

    @Column
    private int capacity;

    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startsAt;

    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    @JsonIgnoreProperties("screenings")
    private Movie movie;

    @OneToMany
    @JoinColumn(name = "ticket_id")
    @JsonIgnoreProperties("screening")
    private List<Ticket> tickets;

    public Screening() {
    }

    public Screening(int screenNumber, int capacity, LocalDateTime startsAt) {
        this.screenNumber = screenNumber;
        this.capacity = capacity;
        this.startsAt = startsAt;
    }

    public Screening(int id, int screenNumber, int capacity, LocalDateTime startsAt) {
        this.id = id;
        this.screenNumber = screenNumber;
        this.capacity = capacity;
        this.startsAt = startsAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
