package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "tickets")
@JsonIgnoreProperties({"customer", "screening"})
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer numSeats;

    @Column
    private LocalDate createdAt;

    @Column
    private LocalDate updatedAt;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIgnoreProperties({"tickets", "id", "phone"})
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "screening_id", nullable = false)
    @JsonIgnoreProperties({"tickets", "id"})
    private Screening screening;

    public Ticket(Integer numSeats, Customer customer, Screening screening){
        this.numSeats = numSeats;
        this.customer = customer;
        this.screening = screening;
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }
    public Ticket(){
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }
}
