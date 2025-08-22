package com.booleanuk.api.cinema.model;

import com.booleanuk.api.cinema.StaticMethods;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "number_of_seats")
    private int numOfSeats;
    @Column(name = "created_at")
    private String createdAt;
    @Column(name = "updated_at")
    private String updatedAt;
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    @JsonIgnoreProperties("tickets")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "screening_id", referencedColumnName = "id")
    @JsonIgnoreProperties("tickets")
    private Screening screening;
    public Ticket() {
    }

    public Ticket(int numOfSeats) {
        this.numOfSeats = numOfSeats;
        this.setCreatedAt();
        this.setUpdatedAt();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNumOfSeats(int numOfSeats) {
        this.numOfSeats = numOfSeats;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Screening getScreening() {
        return screening;
    }

    public void setCreatedAt() {
        this.createdAt = StaticMethods.convertEpochTimeToDateTime(System.currentTimeMillis());
    }

    public void setUpdatedAt() {
        this.updatedAt = StaticMethods.convertEpochTimeToDateTime(System.currentTimeMillis());
    }

    public int getId() {
        return id;
    }

    public int getNumOfSeats() {
        return numOfSeats;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setScreening(Screening screening) {
        this.screening = screening;
    }
}
