package com.booleanuk.api.cinema.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "tickets")
public class Ticket {
    // Fields
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "screening_id", nullable = false)
    @JsonIgnore
    private Screening screening;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIgnore
    private Customer customer;

    @Column (name = "num_seats")
    private int numSeats;
    @CreationTimestamp
    private Timestamp createdAt;
    @UpdateTimestamp
    private Timestamp updatedAt;

    // Constructors
    public Ticket(){
        super();
    }

    public Ticket(int numSeats) {
        this.numSeats = numSeats;
    }

    public Ticket(Screening screening, Customer customer, int numSeats) {
        this.screening = screening;
        this.customer = customer;
        this.numSeats = numSeats;
    }

    // Getters & Setters
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Screening getScreening() {
        return this.screening;
    }

    public void setScreening(Screening screening) {
        this.screening = screening;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getNumSeats() {
        return this.numSeats;
    }

    public void setNumSeats(int numSeats) {
        this.numSeats = numSeats;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
