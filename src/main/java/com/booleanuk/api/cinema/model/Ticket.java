package com.booleanuk.api.cinema.model;
import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "customerId", nullable = false)
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "screeningId", nullable = false)
    private Screening screening;
    @Column(name = "numSeats")
    private int numSeats;
    @Column(name = "createdAt")
    private LocalDateTime createdAt;
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;
    public Ticket() {super();}

    public Ticket(Customer customer, Screening screening, int numSeats, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.customer = customer;
        this.screening = screening;
        this.numSeats = numSeats;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public Ticket(int id) { this.id = id; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumSeats() {
        return numSeats;
    }

    public void setNumSeats(int numSeats) {
        this.numSeats = numSeats;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public Screening getScreening() { return screening; }
    public void setScreening(Screening screening) { this.screening = screening; }
}
