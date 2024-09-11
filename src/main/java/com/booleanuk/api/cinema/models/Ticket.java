package com.booleanuk.api.cinema.models;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private int customerId;
    @Column(nullable = false)
    private int screeningId;
    @Column(nullable = false)
    private int numSeats;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    public Ticket(int customerId, int screeningId, int numSeats) {
        this.customerId = customerId;
        this.screeningId = screeningId;
        this.numSeats = numSeats;
    }

    public Ticket(int id) {
        this.id = id;
    }
}
