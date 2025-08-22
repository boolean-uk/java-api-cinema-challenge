package com.booleanuk.api.cinema.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@NoArgsConstructor
@Entity
@Data
@Table(name="tickets")

public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnoreProperties({"tickets"})
    User customer;

    @ManyToOne
    @JoinColumn(name = "screening_id")
    @JsonIgnoreProperties({"tickets"})
    Screening screening;

    @NotNull
    Integer numSeats;

    OffsetDateTime created;

    OffsetDateTime updatedAt;

    public Ticket(User customer, Screening screening, int numSeats) {
        this.customer = customer;
        this.screening = screening;
        this.numSeats = numSeats;
    }
}
