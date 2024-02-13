package com.booleanuk.api.cinema.model;


import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;



@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private int numSeats;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;


    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIncludeProperties(value = {"id", "name", "email", "phone"})
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "screening_id", nullable = false)
    @JsonIncludeProperties(value = {"id", "screenNumber", "capacity", "startsAt"})
    private Screening screening;

    public Ticket(int numSeats) {
        this.numSeats = numSeats;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Ticket(Integer id) {
        this.id = id;
    }

    public void updateTicket() {
        this.updatedAt = LocalDateTime.now();
    }


}
