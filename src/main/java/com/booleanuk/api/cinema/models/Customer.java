package com.booleanuk.api.cinema.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

/**
 * Class holding information about a customer
 */
@NoArgsConstructor
@Getter
@Setter
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
    private ZonedDateTime createdAt;
    @PrePersist
    protected void onCreate()   {
        updatedAt = createdAt = ZonedDateTime.now();
    }

    @Column
    @UpdateTimestamp
    private ZonedDateTime updatedAt;
    @PreUpdate
    protected void onUpdate()   {
        updatedAt = ZonedDateTime.now();
    }

    public Customer(
            String name,
            String email,
            String phone
    )
    {
        this.name  = name;
        this.email = email;
        this.phone = phone;
    }

    public boolean verifyCustomer() {
        return this.name != null
                && this.email != null
                && this.phone != null;
    }
}
