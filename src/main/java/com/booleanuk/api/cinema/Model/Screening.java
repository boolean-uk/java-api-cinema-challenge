package com.booleanuk.api.cinema.Model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Entity
@Table(name = "screenings")
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name= "movie_id", nullable = false)
    // @JsonIncludeProperties(value = {"id"})
    private Movie movie;

    @OneToMany(mappedBy = "screening")
    // @JsonIncludeProperties(value = {"screenNumber", "startsAt", "capacity"})
    private List<Ticket> tickets;

    @Column
    private Integer screenNumber;
    @Column
    private LocalDate startsAt;
    @Column
    private Integer capacity;
    @Column
    private LocalDate createdAt;
    @Column
    private LocalDate updatedAt;

    public Screening(Integer screenNumber, LocalDate startsAt, Integer capacity) {
        this.screenNumber = screenNumber;
        this.startsAt = startsAt;
        this.capacity = capacity;
        LocalDate datetimeNow = LocalDate.now();
        this.createdAt = datetimeNow;
        this.updatedAt = datetimeNow;
    }

    public Screening(Movie movie, Integer screenNumber, LocalDate startsAt, Integer capacity) {
        this.movie = movie;
        this.screenNumber = screenNumber;
        this.startsAt = startsAt;
        this.capacity = capacity;
        LocalDate datetimeNow = LocalDate.now();
        this.createdAt = datetimeNow;
        this.updatedAt = datetimeNow;
    }

    public Screening(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getScreenNumber() {
        return screenNumber;
    }

    public void setScreenNumber(Integer screenNumber) {
        this.screenNumber = screenNumber;
    }

    public LocalDate getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(LocalDate startsAt) {
        this.startsAt = startsAt;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void updateUpdatedAt() {
        this.updatedAt = LocalDate.now();
    }
}
