package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "num_seats")
    private int numSeats;

    @ManyToOne
    @JoinColumn(name = "customer_id,", nullable = false)
    @JsonIncludeProperties(value = {"id", "name", "email", "phone"})
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "screening_id", nullable = false)
    @JsonIncludeProperties(value = {"id", "screen_number", "capacity", "starts_at"})
    private Screening screening;

    public Ticket(int numSeats) {
        this.numSeats = numSeats;
    }

    public Ticket(long id) {
        this.id = id;
    }
}
