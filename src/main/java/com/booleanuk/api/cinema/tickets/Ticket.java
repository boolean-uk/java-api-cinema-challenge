package com.booleanuk.api.cinema.tickets;

import com.booleanuk.api.cinema.customers.Customer;
import com.booleanuk.api.cinema.screenings.Screening;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private int numSeats;
    @Column
    private LocalDateTime createdAt;
    @Column
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIncludeProperties({"id", "name", "phone"})
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "screening_id")
    @JsonIncludeProperties({"screen_number", "starts_at"})
    private Screening screening;

    public Ticket(int id, int numSeats) {
        this.numSeats = numSeats;
    }

    public Ticket(int id) {
        this.id = id;
    }
}