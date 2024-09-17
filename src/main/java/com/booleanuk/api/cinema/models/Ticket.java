package com.booleanuk.api.cinema.models;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIncludeProperties(value = {"name"})
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "screening_id", nullable = false)
    @JsonIncludeProperties(value = {"screenNumber"})
    private Screening screening;

    @Column
    private int numSeats;

    // Resource timestamps:
    // - https://docs.jboss.org/hibernate/orm/6.1/userguide/html_single/Hibernate_User_Guide.html#mapping-generated

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Ticket(int numSeats) {
        this.numSeats = numSeats;
    }
}
