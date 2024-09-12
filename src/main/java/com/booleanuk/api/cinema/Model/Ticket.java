package com.booleanuk.api.cinema.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @ManyToOne
    @JoinColumn(name="customerId", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name="screeningId", nullable = false)
    private Screening screening;
}
