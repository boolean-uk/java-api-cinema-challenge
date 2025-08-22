package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "customers")
@JsonIgnoreProperties({"tickets"})
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column
    private LocalDate createdAt;

    @Column
    private LocalDate updatedAt;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Ticket> tickets;

    public Customer(String name, String email, String phone){
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }

    public Customer(){
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }
}
