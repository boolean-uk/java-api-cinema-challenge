package com.booleanuk.api.cinema.ticket.model;

import com.booleanuk.api.cinema.customer.model.Customer;
import com.booleanuk.api.cinema.screening.model.Screening;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JsonIncludeProperties(value = {})
    @JoinColumn(name = "customerId", nullable = false)
    private Customer customer;

    @ManyToOne
    @JsonIncludeProperties(value = {})
    @JoinColumn(name = "screeningId", nullable = false)
    private Screening screening;

    @Column(name = "numberOfSeats")
    private int numberOfSeats;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;
}
