package com.booleanuk.api.cinema.ticket;

import com.booleanuk.api.cinema.customer.Customer;
import com.booleanuk.api.cinema.screening.Screening;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
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
    private LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name ="screening_id", nullable = false)
    @JsonIncludeProperties(value = {"startsAt", "capacity"})
    @JsonIgnoreProperties("tickets")
    private Screening screening;

    @ManyToOne
    @JoinColumn(name ="customer_id", nullable = false)
    @JsonIncludeProperties(value = {"name", "email", "phone"})
    @JsonIgnoreProperties("tickets")
    private Customer customer;

    public Ticket(int numSeats) {
        this.numSeats = numSeats;
    }

}
