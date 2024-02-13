package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "screenings")
public class Screening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private int screenNumber;

    @Column
    private LocalDateTime startsAt;

    @Column
    private int capacity;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    @JsonIncludeProperties(value = {"id", "title", "rating", "description", "runtimeMins"})
    private Movie movie;


    public Screening(int screenNumber, LocalDateTime startsAt, int capacity) {
        this.screenNumber = screenNumber;
        this.capacity = capacity;
        this.startsAt = startsAt;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Screening(Integer id) {
        this.id = id;
    }

    public void updateScreening() {
        this.updatedAt = LocalDateTime.now();
    }
}
