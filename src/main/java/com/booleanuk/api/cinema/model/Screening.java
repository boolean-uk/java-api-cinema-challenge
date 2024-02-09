package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
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

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @Column
    private int screenNumber;

    @Column
    private LocalDateTime startsAt;

    @Column
    private int capacity;

    @OneToMany(mappedBy = "screening")
    @JsonIncludeProperties(value = {})
    private List<Ticket> tickets;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Screening(int screenNumber, int capacity, LocalDateTime startsAt) {
        this.screenNumber = screenNumber;
        this.startsAt = startsAt;
        this.capacity = capacity;
    }
}
