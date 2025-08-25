package com.booleanuk.api.cinema.model;

import com.booleanuk.api.cinema.views.Views;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
@Table(name = "tickets")
@Data
@NoArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.Detailed.class)
    private int id;

    @Column
    private int customerId;

    @Column
    private int screeningId;

    @Column
    @JsonView(Views.BasicInfo.class)
    private int numSeats;

    @Column
    @JsonView(Views.Detailed.class)
    private OffsetDateTime createdAt;

    @Column
    @JsonView(Views.Detailed.class)
    private OffsetDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        createdAt = OffsetDateTime.now();
        updatedAt = OffsetDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }

    public Ticket(int id) {
        this.id = id;
    }

    public Ticket(int customerId, int screeningId, int numSeats) {
        this.customerId = customerId;
        this.screeningId = screeningId;
        this.numSeats = numSeats;
    }





    //    TODO link back to customer and screening
}
