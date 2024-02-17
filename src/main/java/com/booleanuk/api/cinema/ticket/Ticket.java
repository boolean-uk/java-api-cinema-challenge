package com.booleanuk.api.cinema.ticket;

import com.booleanuk.api.cinema.customer.Customer;
import com.booleanuk.api.cinema.screening.Screening;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

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
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ssXXX")
    private OffsetDateTime createdAt;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ssXXX")
    private OffsetDateTime updatedAt;

    @ManyToOne  //en ticket har endast en customer
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIncludeProperties(value = {"name", "email", "phone"})    //inkludera dessa fält från customer vid api anrop
    private Customer customer;

    @ManyToOne  //en ticket har endast en screening
    @JoinColumn(name = "screening_id", nullable = false)
    @JsonIncludeProperties(value = {"screenNumber", "startsAt", "capacity"})    //inkludera dessa fält från screening vid api anrop
    private Screening screening;

    @PrePersist
    public void onCreate() {
        OffsetDateTime currentTime = OffsetDateTime.now();
        this.createdAt = currentTime;
        this.updatedAt = currentTime;
    }

    @PreUpdate
    public void onUpdate() {
        OffsetDateTime currentTime = OffsetDateTime.now();
        this.updatedAt = currentTime;
    }

    public Ticket(int numSeats) {
        this.numSeats = numSeats;
    }
}
