package com.booleanuk.api.cinema.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
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
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Column
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Customer(String name, String email, String phone){
        LocalDateTime currentDateTime = LocalDateTime.now(ZoneOffset.UTC);
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.createdAt = currentDateTime;
        this.updatedAt = this.createdAt;
    }

}
