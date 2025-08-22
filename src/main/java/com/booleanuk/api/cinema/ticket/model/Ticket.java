package com.booleanuk.api.cinema.ticket.model;

import com.booleanuk.api.cinema.customer.model.Customer;
import com.booleanuk.api.cinema.screening.model.Screening;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "numberOfSeats is required")
    @Column(name = "numberOfSeats")
    private int numberOfSeats;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "customerId", nullable = false)
    @JsonBackReference(value = "customer-tickets")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "screeningId", nullable = false)
    @JsonBackReference(value = "movie tickets")
    private Screening screening;

    @PrePersist
    private void onCreate() {
        /*
        This method is called before the entity manager saves the entity to the database.
         */
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
