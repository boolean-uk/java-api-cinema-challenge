package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class Ticket extends TimestampedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @JsonProperty(index = 1)
    private long id;

    @NotNull
    @JsonProperty(index = 2)
    private int numSeats;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "screening_id")
    @JsonIgnore
    private Screening screening;

    public void setCustomer(Customer customer) { this.customer = customer;}
    public void setScreening(Screening screening) {this.screening = screening;}
}
