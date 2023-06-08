package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
public class Ticket {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "num_seats")
    private int numSeats;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "customer_id")
//    @JsonIgnoreProperties(value = {"movies"})
    @JsonIgnoreProperties(value = {"email", "phone"})
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "screening_id")
//    @JsonIgnoreProperties(value = {"movies"})
    @JsonIgnoreProperties(value = {"capacity"})
    private Screening screening;

    @ManyToOne
    @JoinColumn(name = "movie_id")
//    @JsonIgnoreProperties(value = {"movies"})
    @JsonIgnoreProperties(value = {"rating","description"})
    private Movie movie;

    // constructors
    public Ticket() {
        super();
    }

    public Ticket(int numSeats){
        super();
        this.numSeats = numSeats;
//        this.createdAt = LocalDateTime.now();
//        this.updatedAt = LocalDateTime.now();
    }

    // getter- and setters
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumSeats() {
        return this.numSeats;
    }

    public void setNumSeats(int numSeats) {
        this.numSeats = numSeats;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Screening getScreening() {
        return this.screening;
    }

    public void setScreening(Screening screening) {
        this.screening = screening;
    }
    public void setScreeningId(Screening screeningId){
        this.screening = screeningId;
    }
}
