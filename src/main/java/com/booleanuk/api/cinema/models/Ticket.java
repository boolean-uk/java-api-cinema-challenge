package com.booleanuk.api.cinema.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int customerId;

    @Column
    private int screeningId;

    @Column
    private int numSeats;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    public Ticket(int customerId, int screeningId, int numSeats) {
        this.customerId = customerId;
        this.screeningId = screeningId;
        this.numSeats = numSeats;
    }

    public Ticket(int id){
        this.id = id;
    }
}
