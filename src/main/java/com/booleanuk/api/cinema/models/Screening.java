package com.booleanuk.api.cinema.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "screenings")
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name= "movie_id", nullable = false)
    @JsonIncludeProperties({"title"})
    private Movie movie;

    @Column(name="screen_number")
    private int screenNumber;

    @Column(name = "starts_at")
    private OffsetDateTime startsAt;

    @Column
    private int capacity;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @OneToMany(mappedBy = "screening", cascade = CascadeType.REMOVE)
    @JsonIncludeProperties({"id"})
    private List<Ticket> tickets;

    public Screening(Movie movie, int screenNumber, OffsetDateTime startsAt, int capacity) {
        this.movie = movie;
        this.screenNumber = screenNumber;
        this.startsAt = startsAt;
        this.capacity = capacity;
        this.createdAt = OffsetDateTime.now();
    }

    public Screening(int id) {
        this.id = id;
    }
}
