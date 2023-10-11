package com.booleanuk.api.cinema.Customer;

import lombok.Data;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;


import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    public Customer(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
}
