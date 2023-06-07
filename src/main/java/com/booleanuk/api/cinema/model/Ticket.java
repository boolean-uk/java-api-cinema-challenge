package com.booleanuk.api.cinema.model;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Screening;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "customer_id",nullable = false)
    @JsonIgnoreProperties("tickets")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "screening_id",nullable = false)
    @JsonIgnoreProperties("tickets")
    private Screening screening;
    @Column(name = "num_seats")
    private int numSeats;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Ticket(){

    }

}
