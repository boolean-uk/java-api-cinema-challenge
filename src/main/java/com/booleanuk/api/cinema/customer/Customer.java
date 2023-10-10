package com.booleanuk.api.cinema.customer;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "customers")
@Getter
@Setter
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

    @Column(name = "createdAt")
    private Date createdAt;

    @Column(name = "updatedAt")
    private Date updatedAt;

    public Customer(int id, String name, String email, String phone, Date createdAt, Date updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Customer() {
    }
}
