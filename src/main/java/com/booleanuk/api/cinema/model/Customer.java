package com.booleanuk.api.cinema.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String phone;

    @Column
    private String createdAt;

    @Column
    private String updatedAt;

    //Needed Fetch Eager to not get json lazy error on delete of Customer, if you know a better way to do it i would like to know :D
    @OneToMany(mappedBy = "customer",fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = {"customer"}, allowSetters = true)
    private List<Ticket> tickets;


    public Customer(String name, String email, String phone, String createdAt, String updatedAt) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Customer(int id) {
        this.id = id;
    }
}
