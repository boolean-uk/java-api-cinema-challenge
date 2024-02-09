package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIncludeProperties(value = {"name", "email", "phone", "createdAt"})
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "screen_id", nullable = false)
    @JsonIncludeProperties(value = {"movie", "screenNumber", "startsAt"})
    private Screening screening;

    @Column(name = "num_seats")
    private int numSeats;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updatedAt;

    public Ticket(Customer customer, Screening screening, int numSeats, Date createdAt, Date updatedAt) {
        this.customer = customer;
        this.screening = screening;
        this.numSeats = numSeats;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Ticket(int id) {
        this.id = id;
    }
}
