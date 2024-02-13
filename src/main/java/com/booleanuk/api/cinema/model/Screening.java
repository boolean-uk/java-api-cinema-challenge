package com.booleanuk.api.cinema.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "screenings")
public class Screening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int screenNumber;

    @Column
    private String startsAt;

    @Column
    private int capacity;

    @Column
    private String createdAt;

    @Column
    private String updatedAt;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    @JsonIncludeProperties(value = {"id", "title", "rating", "description", "runtimeMins", "createdAt", "updatedAt"})
    private Movie movie;


    @OneToMany(mappedBy = "screening")
    @JsonIgnoreProperties(value = {"screening"}, allowSetters = true)
    private List<Ticket> tickets;

    public Screening(int screenNumber, String startsAt, int capacity, String createdAt, String updatedAt, Movie movie) {
        this.screenNumber = screenNumber;
        this.startsAt = startsAt;
        this.capacity = capacity;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.movie = movie;
    }


    public Screening(int id) {
        this.id = id;
    }
}
