package com.booleanuk.api.cinema.customer.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "customers")
public class Customer {

    public Customer (String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "createdAt", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updatedAt", nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist
    private void onCreate() {
        /*
        This method is called before the entity manager saves the entity to the database.
         */
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = OffsetDateTime.now();
    }
}
