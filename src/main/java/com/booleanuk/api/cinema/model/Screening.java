package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "screenings")
@JsonIgnoreProperties({"movie", "tickets"})
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer screenNumber;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false)
    private LocalDate startsAt;

    @Column
    private LocalDate createdAt;

    @Column
    private LocalDate updatedAt;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    @JsonIgnoreProperties({"id", "screenings"})
    private Movie movie;

    @OneToMany(mappedBy = "screening", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Ticket> tickets;

    public Screening(Integer screenNumber, Integer capacity, LocalDate startsAt){
        this.screenNumber = screenNumber;
        this.capacity = capacity;
        this.startsAt = startsAt;
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }

    public Screening(Integer screenNumber, Integer capacity, LocalDate startsAt, Movie movie){
        this.screenNumber = screenNumber;
        this.capacity = capacity;
        this.startsAt = startsAt;
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
        this.movie = movie;
    }

    public Screening(){
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }
}
