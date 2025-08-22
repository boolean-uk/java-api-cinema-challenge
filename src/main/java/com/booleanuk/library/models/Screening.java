package com.booleanuk.library.models;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
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
    private LocalDateTime startsAt;

    @Column
    private int capacity;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime  updatedAt;


    @ManyToOne
    @JoinColumn(name="movie_id", nullable = true)
    @JsonIncludeProperties(value={"created_at","description","author","runtime_mins","title","updated_at"})
    private Movie movie;



    public Screening(int screenNumber,int capacity, LocalDateTime startsAt) {
        this.capacity = capacity;
        this.startsAt = startsAt;
        this.screenNumber = screenNumber;
    }



    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }


    public Screening(int id) {
        this.id = id;
    }
}
