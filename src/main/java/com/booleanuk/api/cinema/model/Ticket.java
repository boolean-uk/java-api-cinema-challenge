package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer numSeats;
    private String createdAt;
    private String updatedAt;


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
    }

    public Ticket(int id){
        this.id = id;
    }
}
