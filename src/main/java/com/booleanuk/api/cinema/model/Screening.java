package com.booleanuk.api.cinema.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "Screening")
public class Screening {
    public Screening (int screenNumber, int capacity, OffsetDateTime startsAt) {
        this.screenNumber = screenNumber;
        this.capacity = capacity;
        this.startsAt = startsAt;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "movieId")
    private int movieId;

    @Column(name = "screenNumber", nullable = false)
    private int screenNumber;

    @Column(name = "startsAt", nullable = false)
    private OffsetDateTime startsAt;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updatedAt", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    private void onCreate() {
        /*
        This method is called before the entity manager saves the entity to the database.
         */
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
