package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
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

    @OneToMany(mappedBy = "customer")
    @JsonIncludeProperties(value = {})
    private List<Ticket> tickets;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Customer(String name, String email, String phone, List<Ticket> tickets) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.tickets = tickets;
    }
}
