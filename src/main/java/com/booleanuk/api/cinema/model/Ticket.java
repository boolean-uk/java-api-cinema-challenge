package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name ="customer_id", nullable = false)
    //@JsonIgnoreProperties(value = {"tickets"})
    @JsonIncludeProperties(value = {"name", "email", "phone"})
    private Customer customer;

    @ManyToOne
    @JoinColumn(name ="screening_id", nullable = false)
    //@JsonIgnoreProperties(value = {"tickets"})
    @JsonIncludeProperties(value = {"movie_id", "startsAt", "capacity"})
    private Screening screening;

    @Column(name = "num_seats")
    private Integer numSeats;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;



    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Ticket(Integer numSeats) {
        this.numSeats = numSeats;
    }
    public Ticket(int id) {
        this.id = id;
    }


}