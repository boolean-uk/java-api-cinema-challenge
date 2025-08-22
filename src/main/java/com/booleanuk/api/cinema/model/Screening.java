package com.booleanuk.api.cinema.model;

import com.booleanuk.api.cinema.StaticMethods;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "screenings")
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "screen_number")
    private int screenNumber;
    private int capacity;
    @Column(name = "starts_at")
    private String startsAt;
    @Column(name = "created_at")
    private String createdAt;
    @Column(name = "updated_at")
    private String updatedAt;
    @ManyToOne
    @JoinColumn(name = "movie_playing")
    @JsonIgnoreProperties("screenings")
    private Movie moviePlaying;
    @OneToMany(mappedBy = "screening")
    @JsonIgnoreProperties("screening")
    private List<Ticket> tickets;
    public Screening(){

    }

    public Screening(int screenNumber, int capacity, String startsAt) {
        this.screenNumber = screenNumber;
        this.capacity = capacity;
        this.startsAt = startsAt;
        this.createdAt = StaticMethods.convertEpochTimeToDateTime(System.currentTimeMillis());
        this.updatedAt = StaticMethods.convertEpochTimeToDateTime(System.currentTimeMillis());
    }

    public Movie getMoviePlaying() {
        return moviePlaying;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setUpdatedAt() {
        //when this method is called, the updatedAt value changes
        this.updatedAt = StaticMethods.convertEpochTimeToDateTime(System.currentTimeMillis());
    }
    public void setScreenNumber(int screenNumber) {
        this.screenNumber = screenNumber;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setStartsAt(String startsAt) {
        this.startsAt = startsAt;
    }

    public int getId() {
        return id;
    }

    public int getScreenNumber() {
        return screenNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getStartsAt() {
        return startsAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setMoviePlaying(Movie moviePlaying) {
        this.moviePlaying = moviePlaying;
    }

}
