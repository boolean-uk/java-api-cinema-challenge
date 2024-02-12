package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
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
    private Integer numSeats;

    //TODO: fix so ouput when get request is correct
    //  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "screening_id", nullable = false)
    @JsonIncludeProperties(value = {"id", "numSeats"})
    private Screening screening;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIncludeProperties(value = {"id", "numSeats"})
    private Customer customer;


    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Ticket(Integer numSeats) {
        this.numSeats = numSeats;
    }

//
//    public String getStartsAt() {
//        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
//        return startsAt.format(outputFormatter);
//    }
//
//    public String getCreatedAt() {
//        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
//        return createdAt.format(outputFormatter);
//    }
//
//    public String getUpdatedAt() {
//        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
//        return updatedAt.format(outputFormatter);
//    }
}

