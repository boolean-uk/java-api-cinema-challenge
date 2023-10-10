package com.booleanuk.api.cinema.ticket;

import com.booleanuk.api.cinema.customer.Customer;
import com.booleanuk.api.cinema.movie.Movie;
import com.booleanuk.api.cinema.screening.Screening;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "tickets")
@Getter
@Setter
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "numSeats")
    private int numSeats;

    @Column(name = "createdAt")
    private Date createdAt;

    @Column(name = "updatedAt")
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "customerId")
    @JsonIgnoreProperties({"tickets"})
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "screeningId")
    @JsonIgnoreProperties({"tickets"})
    private Screening screening;

    public Ticket(int id, int numSeats, Date createdAt, Date updatedAt, Customer customer, Screening screening) {
        this.id = id;
        this.numSeats = numSeats;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.customer = customer;
        this.screening = screening;
    }

    public Ticket() {
    }
}
