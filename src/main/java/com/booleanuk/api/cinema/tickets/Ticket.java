package com.booleanuk.api.cinema.tickets;

import com.booleanuk.api.cinema.customers.Customer;
import com.booleanuk.api.cinema.screenings.Screening;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "tickets")
@JsonIgnoreProperties({"screening", "customer"})
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int numSeats;

    @Column
    private LocalDate createdAt;

    @Column
    private LocalDate updatedAt;

    @ManyToOne
    @JoinColumn(name="screening")
    private Screening screening;

    @ManyToOne
    @JoinColumn(name="customer")
    private Customer customer;


    public Ticket(Customer customer, Screening screening, int numSeats) {
        this.customer = customer;
        this.screening = screening;
        this.numSeats = numSeats;
        createdAt = LocalDate.now();
        updatedAt = LocalDate.now();
    }

    public Ticket(int id) {
        this.id = id;
    }

    public Ticket() {
        createdAt = LocalDate.now();
        updatedAt = LocalDate.now();
    }
}
