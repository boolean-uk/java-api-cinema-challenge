package com.booleanuk.api.cinema.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "screenings")
public class Screening {
    //region // FIELDS //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "screen_number")
    private int screenNumber;
    @Column(name = "starts_at", nullable = false)
    private String startsAt;
    @Column(name = "capacity")
    private int capacity;
    @Column(name = "created_at", nullable = false)
    private String createdAt;
    @Column(name = "updated_at", nullable = false)
    private String updatedAt;
    @ManyToOne
    @JoinColumn(name = "movie_id")
    @JsonIgnoreProperties("screenings")
    private Movie movie;
    @OneToMany(mappedBy = "screening")
    @JsonIgnoreProperties("screening")
    private List<Ticket> tickets;
    //endregion

    //region // PROPERTIES //
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

    public String getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(String startsAt) {
        this.startsAt = startsAt;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
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
    //endregion

    public Screening() {
        this.createdAt = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("CET")).toString();
        this.updatedAt = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("CET")).toString();
    }
    public Screening(int id) {
        this.id = id;
        this.createdAt = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("CET")).toString();
        this.updatedAt = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("CET")).toString();
    }
    public Screening(int screenNumber, String startsAt, int capacity, Movie movie) {
        this.screenNumber = screenNumber;
        this.startsAt = startsAt;
        this.capacity = capacity;
        this.movie = movie;
        this.createdAt = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("CET")).toString();
        this.updatedAt = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("CET")).toString();
    }
}