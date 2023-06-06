package com.booleanuk.api.cinema.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "tickets")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="customer_id", nullable=false)
    @JsonIgnore
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "screening_id",nullable = false)
    @JsonIgnore
    private Screening screening;

    @Column
    private int numSeats;

    @Column
    @CreationTimestamp
    private Instant createdAt;
    @Column
    @UpdateTimestamp
    private Instant updatedAt;
}
