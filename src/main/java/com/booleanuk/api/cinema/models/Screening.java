package com.booleanuk.api.cinema.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
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

    @Column(insertable = false, updatable = false)
    private int movie_id;

    @OneToMany(mappedBy = "screening", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties({"screening"})
    private List<Ticket> tickets;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    @JsonIncludeProperties(value = {"title", "runtimeMins"})
    private Movie movie;
}
