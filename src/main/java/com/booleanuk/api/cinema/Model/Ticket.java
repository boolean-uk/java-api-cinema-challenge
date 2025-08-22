package com.booleanuk.api.cinema.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private int id;
    @Setter
    @Getter
    @Column(name = "customerId")
    private int customerId;
    @Setter
    @Getter
    @Column(name = "screeningId")
    private int screeningId;
    @Setter
    @Getter
    @Column(name = "numSeats")
    private int numSeats;
    @Setter
    @Getter
    @Column(name = "createdAt")
    private LocalDateTime createdAt;
    @Setter
    @Getter
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;




}
