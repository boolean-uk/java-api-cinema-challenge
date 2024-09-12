package com.booleanuk.api.cinema.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String email;
    private String phone;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public Customer(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.createdAt = OffsetDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        this.updatedAt = OffsetDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    }

    public Customer(int id){
        this.id = id;
    }

    public Customer(){
        this.createdAt = OffsetDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        this.updatedAt = OffsetDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    }
}
