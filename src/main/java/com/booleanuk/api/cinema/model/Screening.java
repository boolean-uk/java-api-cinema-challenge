package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "screenings")
public class Screening extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    @JsonIncludeProperties(value = {"title", "rating", "description", "runtimeMinutes"})
    private Movie movie;

    @Column(name = "screen_number")
    private int screenNumber;
    @Column(name = "starts_at")
    private Date startsAt;
    @Column(name = "capacity")
    private int capacity;

    @OneToMany(mappedBy = "screening")
    @JsonIgnoreProperties("screening")
    private List<Ticket> tickets;

    public Screening(Movie movie, int screenNumber, Date startsAt, int capacity) {
        this.movie = movie;
        this.screenNumber = screenNumber;
        this.startsAt = startsAt;
        this.capacity = capacity;
    }

    public Screening(int id) {
        this.id = id;
    }
}
