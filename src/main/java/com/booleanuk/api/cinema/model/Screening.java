package com.booleanuk.api.cinema.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name ="screenings")
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;
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

    // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Screening(Movie movie, int screenNumber, int capacity, String startsAt) {
        this.movie = movie;
        this.screenNumber = screenNumber;
        this.capacity = capacity;
        this.startsAt = LocalDateTime.parse(startsAt);
    }
}
