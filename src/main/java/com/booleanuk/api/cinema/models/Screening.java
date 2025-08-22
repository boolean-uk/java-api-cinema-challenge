package com.booleanuk.api.cinema.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "screenings")
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int screenNumber;

    @Column
    private int capacity;

    @Column
    private String startsAt;

    @Column
    private String createdAt;

    @Column
    private String updatedAt;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @OneToMany(mappedBy = "screening")
    @JsonIgnoreProperties({"screening"})
    private List<Ticket> tickets;

    public Screening(int screenNumber, int capacity, String startsAt) {
        this.screenNumber = screenNumber;
        this.capacity = capacity;
        this.startsAt = startsAt;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = String.valueOf(LocalDateTime.now());
        this.updatedAt = String.valueOf(LocalDateTime.now());
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = String.valueOf(LocalDateTime.now());
    }

    public Screening(int id) {this.id = id;}

    public void addTicket(Ticket ticket) {
        this.tickets.add(ticket);
    }
}
