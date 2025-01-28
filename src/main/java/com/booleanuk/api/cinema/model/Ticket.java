package com.booleanuk.api.cinema.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Screening screening;

    @Column int numSeats;
    @Column LocalDateTime createdAt;
    @Column LocalDateTime updatedAt;

    public Ticket(int id, Customer customer, Screening screening, int numSeats, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.customer = customer;
        this.screening = screening;
        this.numSeats = numSeats;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Ticket(Customer customer, Screening screening, int numSeats, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.customer = customer;
        this.screening = screening;
        this.numSeats = numSeats;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Ticket(int numSeats, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.numSeats = numSeats;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Ticket(int id) {
        this.id = id;
    }

    public Ticket() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Screening getScreening() {
        return screening;
    }

    public void setScreening(Screening screening) {
        this.screening = screening;
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
}
