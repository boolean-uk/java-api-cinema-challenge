package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customers")
@JsonIgnoreProperties("tickets")
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
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private ZonedDateTime createdAt;

    @Column
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private ZonedDateTime updatedAt;

    @OneToMany(mappedBy = "customer")
    @JsonIgnoreProperties("customer")
    private List<Ticket> tickets;

    @PrePersist
    public void prePersist() {
        this.createdAt = ZonedDateTime.now();
        this.updatedAt = ZonedDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = ZonedDateTime.now();
    }

    public Customer(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public Customer(int id) {
        this.id = id;
    }

    public ZonedDateTime getCreatedAt() {
        return this.updatedAt.withZoneSameInstant(ZoneId.systemDefault());
    }
    public ZonedDateTime getUpdatedAt() {
        return this.updatedAt.withZoneSameInstant(ZoneId.systemDefault());
    }
}
