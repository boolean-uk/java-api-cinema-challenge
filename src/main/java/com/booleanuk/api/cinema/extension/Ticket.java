package com.booleanuk.api.cinema.extension;


import com.booleanuk.api.cinema.models.Customer;
import com.booleanuk.api.cinema.models.Screening;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
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
    private int numSeats;

    @Column
    private String createdAt;

    @ManyToOne
    @JoinColumn(name = "customer_id",nullable = false)
    @JsonIncludeProperties(value = {"name"})
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "screening_id",nullable = false)
    @JsonIncludeProperties(value = {"id","screenNumber"})
    private Screening screening;

    public Ticket(int numSeats) {
        this.numSeats = numSeats;
    }
}
