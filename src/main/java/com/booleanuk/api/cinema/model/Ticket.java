package com.booleanuk.api.cinema.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // TODO: Add reference.
    @Column(name = "customer_id")
    private Customer customer;

    @Column(name = "screening_id")
    private Screening screening;


}
