package com.booleanuk.api.cinema.models;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name= "user_id", nullable = false)
    @JsonIncludeProperties({"username"})
    private User user;

    @ManyToOne
    @JoinColumn(name= "screening_id", nullable = false)
    @JsonIncludeProperties({"id"})
    private Screening screening;

    @Column(name= "num_seats")
    private int numSeats;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    public Ticket(User user, Screening screening, int numSeats, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.user = user;
        this.screening = screening;
        this.numSeats = numSeats;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Ticket(User user, Screening screening, int numSeats) {
        this.user = user;
        this.screening = screening;
        this.numSeats = numSeats;
        this.createdAt = OffsetDateTime.now();
    }

    public Ticket(int id) {
        this.id = id;
    }
}
