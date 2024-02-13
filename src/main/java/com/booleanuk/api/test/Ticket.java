package com.booleanuk.api.test;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private int customerId;
    @Column
    private int screeningsId;
    @Column
    private int numSeats;
    @Column
    private LocalDateTime createdAt;
    @Column
    private LocalDateTime updatedAt;

    public Ticket(int numSeats) {
        this.numSeats = numSeats;
    }
}
