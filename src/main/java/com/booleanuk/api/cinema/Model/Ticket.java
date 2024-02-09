package com.booleanuk.api.cinema.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name= "costumer_id", nullable = false)
    @JsonIgnore
    private Customer customer;

    @ManyToOne
    @JoinColumn(name= "screening_id", nullable = false)
    @JsonIgnore
    private Screening screening;

    @Column
    private Integer numSeats;
    @Column
    private String createdAt;
    @Column
    private String updatedAt;

    public Ticket(Integer numSeats) {
        this.numSeats = numSeats;
        String datetimeNow = LocalDate.now().toString();
        this.createdAt = datetimeNow;
        this.updatedAt = datetimeNow;
    }

    public Ticket(Customer customer, Screening screening, Integer numSeats) {
        this.customer = customer;
        this.screening = screening;
        this.numSeats = numSeats;
        String datetimeNow = LocalDateTime.now().toString();
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

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void updateUpdatedAt() {
        this.updatedAt = LocalDateTime.now().toString();
    }
}
