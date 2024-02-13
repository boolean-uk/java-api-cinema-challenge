package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "tickets")
@Getter
@Setter
@NoArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false )
    @JsonIncludeProperties(value = {"id","name", "email", "phone", "createdAt", "updatedAt"})
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "screening_id", nullable = false )
    @JsonIncludeProperties(value = {"id","movieId", "screenNumber", "startsAt", "capacity", "createdAt", "updatedAt"})
    private Screening screening;

    @Column(name = "num_seats")
    private int numSeats;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    public Ticket(int id) {
        this.id = id;
    }

    public Ticket(int numSeats, Date createdAt) {
        this.numSeats = numSeats;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }
}
