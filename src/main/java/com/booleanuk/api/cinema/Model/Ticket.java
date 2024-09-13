package com.booleanuk.api.cinema.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int numSeats;

    @Column
    private LocalDateTime createdAt=LocalDateTime.now();

    @Column
    private LocalDateTime updatedAt=LocalDateTime.now();

    @ManyToOne
    @JsonIgnoreProperties(value ={"screenings", "tickets", "movie","customer","screening"})
    @JoinColumn(name="customerId", nullable = false)
    private Customer customer;

    @ManyToOne
    @JsonIgnoreProperties(value ={"screenings", "tickets", "movie","customer","screening"})
    @JoinColumn(name="screeningId", nullable = false)
    private Screening screening;

    public Ticket(int numSeats) {
        this.numSeats = numSeats;
        createdAt=LocalDateTime.now();
        updatedAt=LocalDateTime.now();
    }
}
