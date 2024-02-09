package com.booleanuk.api.cinema.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Column
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @Column
    private int numSeats;


    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "screening_id", nullable = false)
    private Screening screening;

    public Ticket(int numSeats){
        LocalDateTime currentDateTime = LocalDateTime.now(ZoneOffset.UTC);
        this.numSeats = numSeats;
        this.createdAt = currentDateTime;
        this.updatedAt = this.createdAt;
    }
}
