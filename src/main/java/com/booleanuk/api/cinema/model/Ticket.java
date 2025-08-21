package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer numSeats;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;


    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIgnoreProperties(value = {"tickets"})
    @JsonIgnore
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "screening_id", nullable = false)
    @JsonIgnoreProperties(value = {"tickets"})
    @JsonIgnore
    private Screening screening;

    public Ticket(Integer numSeats) {
        this.numSeats = numSeats;
        this.createdAt = OffsetDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        this.updatedAt = OffsetDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    }

    public Ticket(int id){
        this.id = id;
    }

    public Ticket () {
        this.createdAt = OffsetDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        this.updatedAt = OffsetDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    }
}
