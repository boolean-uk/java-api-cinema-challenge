package com.booleanuk.api.cinema.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
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

    private ERole role = ERole.ROLE_USER; // change to customer

    @OneToMany(mappedBy = "customer")
    @JsonIgnoreProperties({"customer"})
    private List<Ticket> tickets;

    public Customer(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = String.valueOf(LocalDateTime.now());
        this.updatedAt = String.valueOf(LocalDateTime.now());
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = String.valueOf(LocalDateTime.now());
    }

    public Customer(int id) {this.id = id;}

    public void addTicket(Ticket ticket) {
        this.tickets.add(ticket);
    }
}
