package com.booleanuk.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    private int customerID;

    @Column
    private int screeningID;

    @Column
    private int numSeats;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern ("yyyy-MM-dd ' ' HH:mm:ss");
        String dateTimeNow = LocalDateTime.now().format(dateTimeFormat);
        this.createdAt = LocalDateTime.parse(dateTimeNow, dateTimeFormat);
        this.updatedAt = LocalDateTime.parse(dateTimeNow, dateTimeFormat);
    }

    @PreUpdate
    public void preUpdate() {
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern ("yyyy-MM-dd ' ' HH:mm:ss");
        String dateTimeNow = LocalDateTime.now().format(dateTimeFormat);
        this.updatedAt = LocalDateTime.parse(dateTimeNow, dateTimeFormat);
    }
}
