package com.booleanuk.api.cinema.models;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(insertable = false, updatable = false)
    private int customer_id;

    @Column(insertable = false, updatable = false)
    private int screening_id;

    @Column
    private int num_seats;

    @ManyToOne
    @JoinColumn(name = "screening_id", nullable = false)
    @JsonIncludeProperties(value = {"screenNumber", "capacity", "startsAt"})
    private Screening screening;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIncludeProperties(value = {"name", "email", "phone"})
    private Customer customer;
}
