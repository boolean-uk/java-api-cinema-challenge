package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private int numSeats;

    @Column
    private String createdAt;

    @Column
    private String updatedAt;


    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false, referencedColumnName = "id")
    @JsonIncludeProperties(value = {"id", "name", "email", "phone", "createdAt", "updatedAt"})
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "screening_id", nullable = false, referencedColumnName = "id")
    @JsonIncludeProperties(value = {"id", "movieId", "screenNumber", "startsAt", "capacity", "createdAt", "updatedAt"})
    private Screening screening;


    public Ticket(int numSeats, String createdAt, String updatedAt, Customer customer, Screening screening) {
        this.numSeats = numSeats;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.customer = customer;
        this.screening = screening;
    }
}

