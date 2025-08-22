package com.booleanuk.api.cinema.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private int id;
    @Setter
    @Getter
    @Column(name = "name")
    private String name;
    @Setter
    @Getter
    @Column(name = "email")
    private String email;
    @Setter
    @Getter
    @Column(name = "phone")
    private String phone;
    @Setter
    @Getter
    @Column(name = "createdAt")
    private LocalDateTime createdAt;
    @Setter
    @Getter
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    public Customer(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;

        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        this.createdAt = LocalDateTime.parse(currentDateTime.format(formatter));
        this.updatedAt = LocalDateTime.parse(currentDateTime.format(formatter));
    }


}
