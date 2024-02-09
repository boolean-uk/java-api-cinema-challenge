package com.booleanuk.api.cinema.Model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name= "costumer_id", nullable = false)
    // @JsonIncludeProperties(value = {"id"})
    private Customer customer;

    @ManyToOne
    @JoinColumn(name= "screening_id", nullable = false)
    // @JsonIncludeProperties(value = {"id"})
    private Screening screening;

    @Column
    private Integer numSeats;
    @Column
    private LocalDate createdAt;
    @Column
    private LocalDate updatedAt;

    public Ticket(Integer numSeats, LocalDate createdAt) {
        this.numSeats = numSeats;
        this.createdAt = createdAt;
        this.updatedAt = createdAt;
    }

    public Ticket(Customer customer, Screening screening, Integer numSeats) {
        this.customer = customer;
        this.screening = screening;
        this.numSeats = numSeats;
        LocalDate datetimeNow = LocalDate.now();
        this.createdAt = datetimeNow;
        this.updatedAt = datetimeNow;
    }

    public Ticket(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Screening getScreening() {
        return screening;
    }

    public void setScreening(Screening screening) {
        this.screening = screening;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void updateUpdatedAt() {
        this.updatedAt = LocalDate.now();
    }
}
