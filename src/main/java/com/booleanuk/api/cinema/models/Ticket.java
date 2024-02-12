package com.booleanuk.api.cinema.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int numSeats;

    @Column
    @CreationTimestamp
    private Date createdAt;
    @PrePersist
    protected void onCreate()   {
        updatedAt = createdAt = new Date();
    }

    @Column
    @UpdateTimestamp
    private Date updatedAt;
    @PreUpdate
    protected void onUpdate()   {
        updatedAt = new Date();
    }

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "screening_id")
    @JsonIgnore
    private Screening screening;

    public Ticket(
            Customer customer,
            Screening screening,
            int numSeats
    ) {
        this.customer  = customer;
        this.screening = screening;
        this.numSeats  = numSeats;
    }

    public Ticket(
            int numSeats
    )   {
        this.numSeats = numSeats;
    }

    public boolean verifyTicket()   {
        return this.customer != null
                && this.screening != null
                && this.numSeats != 0;
    }
}
