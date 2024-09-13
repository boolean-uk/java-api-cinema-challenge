package com.booleanuk.api.cinema.customer.model;

import com.booleanuk.api.cinema.ticket.model.Ticket;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

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
        this.tickets = new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "name is required")
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "email is required")
    @Column(name = "email", nullable = false)
    private String email;

    @NotBlank(message = "phone is required")
    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "createdAt", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updatedAt", nullable = false)
    private OffsetDateTime updatedAt;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "customer-tickets")
    List<Ticket> tickets;

    @PrePersist
    private void onCreate() {
        /*
        This method is called before the entity manager saves the entity to the database.
         */
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = OffsetDateTime.now();
    }
}
