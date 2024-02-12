package com.booleanuk.api.cinema.ticket;

import com.booleanuk.api.cinema.customer.Customer;
import com.booleanuk.api.cinema.screening.Screening;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIncludeProperties(value = {"name", "phone"})
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "screening_id", nullable = false)
    @JsonIncludeProperties(value = {"movie", "screenNumber", "startsAt"})
    private Screening screening;

    @Column
    private int numSeats;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    public Ticket(int numSeats){
        setNumSeats(numSeats);
    }
}
