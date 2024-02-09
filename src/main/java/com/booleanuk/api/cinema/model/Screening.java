package com.booleanuk.api.cinema.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "screenings")
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "movie_id")
    private int movie;
    @Column(name = "screen_number")
    private int screenNumber;
    @Column(name = "starts_at")
    private Date startsAt;
    @Column(name = "capacity")
    private int capacity;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updatedAt;

    public Screening(int movie, int screenNumber, Date startsAt, int capacity, Date createdAt, Date updatedAt) {
        this.movie = movie;
        this.screenNumber = screenNumber;
        this.startsAt = startsAt;
        this.capacity = capacity;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Screening(int id) {
        this.id = id;
    }
}
