package com.booleanuk.api.cinema.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "screenings")
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private Integer screenNumber;
    @Column(nullable = false)
    private Integer capacity;
    @Column(nullable = false)
    private LocalDateTime startsAt;
    @Column
    private LocalDateTime createdAt;
    @Column
    private LocalDateTime updatedAt;

    public Screening(int id){
        this.id = id;
    }

    public Screening(Integer screenNumber, Integer capacity, LocalDateTime startsAt) {
        this.screenNumber = screenNumber;
        this.capacity = capacity;
        this.startsAt = startsAt;
        if (createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
        this.updatedAt = LocalDateTime.now();
    }
}
