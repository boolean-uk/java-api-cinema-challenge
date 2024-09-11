package com.booleanuk.api.cinema.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "numSeats")
    private Integer numSeats;

    @ManyToOne(cascade = { CascadeType.ALL })
    private Movie movie;

    @ManyToOne(cascade = { CascadeType.ALL })
    private  Customer customer;

    public Ticket(Integer numSeats) {
        this.numSeats = numSeats;
    }

    public Ticket() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumSeats() {
        return numSeats;
    }

    public void setNumSeats(Integer numSeats) {
        this.numSeats = numSeats;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
