package com.booleanuk.api.cinema.customers;


import com.booleanuk.api.cinema.screens.Screen;
import com.booleanuk.api.cinema.tickets.Ticket;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "date_created")
    private String date_created;

    @Column(name = "date_updated")
    private String date_updated;

    @OneToMany(mappedBy = "customer")
    @JsonIgnoreProperties({"id", "customer"})
    private List<Ticket> tickets;

    public Customer(int id) {
        this.id = id;
    }
}
