package com.booleanuk.api.cinema.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "customers")
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    private Instant createdAt;
    @Column
    @UpdateTimestamp
    private Instant updatedAt;
    // FK
    @OneToMany(mappedBy = "customer")
//    @JsonIgnore
    private List<Ticket> tickets;

}
