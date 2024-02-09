package com.booleanuk.api.cinema.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    @JsonIgnore
    private Movie movie;

    // @OneToMany(mappedBy = "screening")
    // @JsonIncludeProperties(value = {"screenNumber", "startsAt", "capacity"})
    //private List<Ticket> tickets;

    @Column
    private Integer screenNumber;
    @Column
    private String startsAt;
    @Column
    private Integer capacity;
    @Column
    private String createdAt;
    @Column
    private String updatedAt;

    public Screening(Integer screenNumber, String startsAt, Integer capacity) {
        this.screenNumber = screenNumber;
        this.startsAt = startsAt;
        this.capacity = capacity;
        String datetimeNow = LocalDateTime.now().toString();
        this.createdAt = datetimeNow;
        this.updatedAt = datetimeNow;
    }

    public Screening(Movie movie, Integer screenNumber, String startsAt, Integer capacity) {
        this.movie = movie;
        this.screenNumber = screenNumber;
        this.startsAt = startsAt;
        this.capacity = capacity;
        String datetimeNow = LocalDateTime.now().toString();
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

    public String getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(String startsAt) {
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

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void updateUpdatedAt() {
        this.updatedAt = LocalDateTime.now().toString();
    }
}
