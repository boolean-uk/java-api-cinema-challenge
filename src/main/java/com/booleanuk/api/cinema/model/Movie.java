package com.booleanuk.api.cinema.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "movies")
public class Movie {
    public Movie (String title, String rating, String description, int runtimeMins) {
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.runtimeMins = runtimeMins;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "rating", nullable = false)
    private String rating;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "runtimeMins", nullable = false)
    private int runtimeMins;

    @Column(name = "createdAt", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updatedAt", nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist
    private void onCreate() {
        /*
        This method is called before the entity manager saves the entity to the database.
         */
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = OffsetDateTime.now();
    }
}
